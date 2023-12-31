import React, {useState} from 'react';
import {
    Box,
    Button, Flex,
    FormControl, FormErrorMessage, FormHelperText,
    FormLabel,
    Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    Select, Spacer,
    useToast
} from "@chakra-ui/react";
import {ObservationResponse} from "../util/models";
import {SingleDatepicker} from "chakra-dayzed-datepicker";
import {HTTP_FORBIDDEN, HTTP_SUCCESS, HTTP_UNAUTHORIZED, SESSION_STORAGE_KEY} from "../util/constants";
import {useNavigate} from "react-router-dom";
import {concatenateDate, extractTime} from "../util/dateUtil";

interface ObservationEditProps {
    isOpen: boolean;
    onClose: () => void;
    onUpdateClose: () => void;
    onOpen: (selectedObservation: any) => void;
    overlay: React.ReactNode;
    observation: ObservationResponse
    isEdit: boolean;
}

const ObservationEditComponent: React.FC<ObservationEditProps> = ({
                                                                      isOpen,
                                                                      onOpen,
                                                                      onUpdateClose,
                                                                      onClose,
                                                                      overlay,
                                                                      observation,
                                                                      isEdit
                                                                  }) => {

    const [date, setDate] = useState<Date>(new Date(Date.UTC(
        new Date(observation.date).getUTCFullYear(),
        new Date(observation.date).getUTCMonth(),
        new Date(observation.date).getUTCDate(),
        new Date(observation.date).getUTCHours(),
        new Date(observation.date).getUTCMinutes(),
        new Date(observation.date).getUTCSeconds(),
        new Date(observation.date).getUTCMilliseconds()
        )
    ));
    const [patient, setPatient] = useState<number>(observation.patient);
    const [measurementType, setMeasurementType] = useState<string>(observation.measurementType);
    const [unit, setUnit] = useState<string>(observation.unit);
    const [error, setError] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const [value, setValue] = useState<string>(observation.value ? observation.value.toString() : "0");
    const [hours, setHours] = useState<string>(extractTime(observation.date).h);
    const [minutes, setMinutes] = useState<string>(extractTime(observation.date).m);

    const toast = useToast();
    const navigation = useNavigate();
    const isValueError = value === ''

    const checkValidHours = hours.length === 0 || minutes.length === 0
        || (hours.length == 2 && parseInt(hours) > 23) || (minutes.length == 2 && parseInt(minutes) > 59);

    const onChangeHours = (e: React.ChangeEvent<HTMLInputElement>): void => {
        if (e.target.value.length < 3) {
            setHours(e.target.value);
        } else {
            setHours('00');
        }
    }

    const onChangeMinutes = (e: React.ChangeEvent<HTMLInputElement>): void => {
        if (e.target.value.length < 3) {
            setMinutes(e.target.value);
        } else {
            setHours('00');
        }
    }

    const onPatientSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.value && e.target.value.length > 0) {
            setPatient(parseInt(e.target.value));
        } else {
            toast({
                title: "Invalid patient ID.",
                description: "Select a valid patient ID",
                status: 'error',
                duration: 9000,
                isClosable: true,
            });
        }
    }

    const errorOcurred = (message: string) => {
        setError(true);
        toast({
            title: 'Error.',
            description: message,
            status: 'error',
            duration: 9000,
            isClosable: true,
        })
        setError(false);
    }

    const handleFailures = (observationReq: Response) => {
        if (observationReq.status === HTTP_UNAUTHORIZED || observationReq.status === HTTP_FORBIDDEN) {
            sessionStorage.removeItem(SESSION_STORAGE_KEY);
            navigation('/', {
                state: {
                    redirect: true
                }
            });
            return false;
        }
        if (observationReq.status !== HTTP_SUCCESS) {
            setError(false);
            return true;
        } else {
            setError(true);
            return false;
        }
    }

    const onObservationSave = async (observation: ObservationResponse) => {
        try {
            setLoading(true);
            const observationReq = await fetch(`/api/observations`,
                {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${sessionStorage.getItem(SESSION_STORAGE_KEY)}`
                    },
                    body: JSON.stringify(observation)
                });
            return handleFailures(observationReq);
        } catch (err) {
            setError(true);
            return false;
        } finally {
            setLoading(false);
            onUpdateClose();
        }
    }

    const onObservationUpdate = async (observation: ObservationResponse) => {
        try {
            setLoading(true);
            const observationReq = await fetch(`/api/observations/${observation.id}`,
                {
                    method: 'PUT',
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${sessionStorage.getItem(SESSION_STORAGE_KEY)}`
                    },
                    body: JSON.stringify(observation)
                });
            return handleFailures(observationReq);
        } catch (err) {
            setError(true);
            return false;
        } finally {
            setLoading(false);
            onUpdateClose();
        }
    }

    const onMeasurementTypeSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.value && e.target.value.length > 0) {
            console.log(`Setting measurement type: ${e.target.value}`);
            setMeasurementType(e.target.value);
            onUnitSelect(e);
        } else {
            toast({
                title: "Invalid measurement type.",
                description: "Select a valid measurement type",
                status: 'error',
                duration: 9000,
                isClosable: true,
            });
        }
    }

    // TODO: Update this with a REST call (or a passed-down prop)
    const patients = [
        101,
        102,
        103,
        104,
        105,
        110,
        120,
        130
    ];

    interface MeasurementType {
        type: string;
        unit: string
    }

    // TODO: Read this from the database
    const measurementTypes: MeasurementType[] = [
        {
            type: "heart-rate",
            unit: "beats/minute"
        },
        {
            type: "skin-temperature",
            unit: "degrees Celcius"
        },
        {
            type: "respiratory-rate",
            unit: "breaths/minute"
        },
    ]


    const onUnitSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const measurement: MeasurementType | undefined = measurementTypes
            .find(measurement => measurement.type === e.target.value);
        if (measurement) {
            setUnit(measurement.unit);
        }
    }

    const customFloat  = (value: string) => {
        if (value) return parseFloat(value);
        else errorOcurred("Error in value field.");
        return 0;
    }

    return (
        <>
            <Modal isCentered isOpen={isOpen} onClose={onClose}>
                {overlay}
                <ModalContent>
                    <ModalHeader>Edit Observation</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody pb={6}>
                        <FormControl>
                            <FormLabel>Patient</FormLabel>
                            <Select placeholder='Select Patient' onChange={onPatientSelect} value={patient}>
                                {patients.map((option, idx) => (
                                    <option key={idx} value={option}>{option}</option>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl mt={4} isInvalid={checkValidHours}>
                            <FormLabel>Date & Time</FormLabel>
                            <Flex gap={6}>
                                <SingleDatepicker onDateChange={setDate} name="date-input"
                                                  date={new Date(date)}/>
                                <Spacer/>
                                <Flex gap={2}>
                                    <Input placeholder="HH" value={hours} onChange={onChangeHours} type="number"/>
                                    <Input placeholder="mm" value={minutes} onChange={onChangeMinutes} type="number"/>
                                </Flex>
                            </Flex>
                        </FormControl>
                        <FormControl mt={4}>
                            <FormLabel>Measurement Type</FormLabel>
                            <Select placeholder='Select Patient' onChange={onMeasurementTypeSelect}
                                    value={measurementType}>
                                {measurementTypes.map((option, idx) => (
                                    <option key={idx} value={option.type}>{option.type}</option>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl mt={4} isInvalid={isValueError}>
                            <FormLabel>Value</FormLabel>
                            <Input placeholder="Value" onChange={(e) => {
                                setValue(e.target.value);
                            }} value={value}/>
                            {!isValueError ? (
                                <FormHelperText>
                                    Value of the parameter.
                                </FormHelperText>
                            ) : (
                                <FormErrorMessage>Value is required.</FormErrorMessage>
                            )}
                        </FormControl>
                        <FormControl mt={4}>
                            <FormLabel>Unit</FormLabel>
                            <Select placeholder='Select Patient' onChange={onUnitSelect} value={unit} disabled={true}>
                                {Object.values(measurementTypes).map((option, idx) => (
                                    <option key={idx} value={option.unit}>{option.unit}</option>
                                ))}
                            </Select>
                        </FormControl>
                    </ModalBody>
                    <ModalFooter>
                        <Button onClick={onClose}>Close</Button>
                        {
                            isEdit && <Button colorScheme="blue" onClick={() => onObservationUpdate(
                                {
                                    id: observation.id,
                                    date: (concatenateDate(date, hours, minutes)).toISOString().split('.')[0] + "Z",
                                    patient: patient,
                                    value: customFloat(value),
                                    measurementType: measurementType,
                                    unit: unit
                                }
                            )} ml={2}>Update</Button>
                        }
                        {
                            !isEdit && <Button colorScheme="green" onClick={() => onObservationSave(
                                {
                                    id: observation.id,
                                    date: (concatenateDate(date, hours, minutes)).toISOString().split('.')[0] + "Z",
                                    patient: patient,
                                    value: customFloat(value),
                                    measurementType: measurementType,
                                    unit: unit
                                }
                            )} ml={2}>Save</Button>
                        }
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ObservationEditComponent;
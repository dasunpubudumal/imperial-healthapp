import React, {useState} from 'react';
import {
    Button, FormControl, FormLabel, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader, Select,
    useToast
} from "@chakra-ui/react";
import {ObservationResponse} from "../util/models";
import {SingleDatepicker} from "chakra-dayzed-datepicker";
import {HTTP_FORBIDDEN, HTTP_SUCCESS, HTTP_UNAUTHORIZED, SESSION_STORAGE_KEY} from "../util/constants";
import {useNavigate} from "react-router-dom";

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

    const [date, setDate] = useState<Date>(new Date(observation.date));
    const [patient, setPatient] = useState<number>(observation.patient);
    const [measurementType, setMeasurementType] = useState<string>(observation.measurementType);
    const [unit, setUnit] = useState<string>(observation.unit);
    const [error, setError] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const [value, setValue] = useState<number>(observation.value);

    const toast = useToast();
    const navigation = useNavigate();

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
            const observationReq = await fetch(`/observations`,
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
            const observationReq = await fetch(`/observations/${observation.id}`,
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
                        <FormControl mt={4}>
                            <FormLabel>Date</FormLabel>
                            <SingleDatepicker onDateChange={setDate} name="date-input"
                                              date={new Date(observation.date)}/>
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
                        <FormControl mt={4}>
                            <FormLabel>Value</FormLabel>
                            <Input placeholder="Value" onChange={(e) => {
                                if (e.target.value && e.target.value.length > 0) setValue(parseInt(e.target.value));
                                else setValue(observation.value);
                            }} value={value}/>
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
                                    date: date.toISOString(),
                                    patient: patient,
                                    value: value,
                                    measurementType: measurementType,
                                    unit: unit
                                }
                            )} ml={2}>Update</Button>
                        }
                        {
                            !isEdit && <Button colorScheme="green" onClick={() => onObservationSave(
                                {
                                    id: observation.id,
                                    date: date.toISOString(),
                                    patient: patient,
                                    value: value,
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
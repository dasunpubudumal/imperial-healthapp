import React, {useEffect, useState} from 'react';
import {
    Button, FormControl, FormLabel, Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader, Select,
    Text, useToast
} from "@chakra-ui/react";
import {ObservationResponse} from "../util/models";
import {SingleDatepicker} from "chakra-dayzed-datepicker";

interface ObservationEditProps {
    isOpen: boolean;
    onClose: () => void;
    onOpen: (selectedObservation: any) => void;
    overlay: React.ReactNode;
    observation: ObservationResponse
}

const ObservationEditComponent: React.FC<ObservationEditProps> = ({isOpen, onOpen, onClose, overlay, observation}) => {

    const [date, setDate] = useState<Date>(new Date());
    const [patient, setPatient] = useState<number>(observation.patient);
    const [measurementType, setMeasurementType] = useState<string>(observation.measurementType);
    const [unit, setUnit] = useState<string>(observation.unit);

    const toast = useToast();

    const onPatientSelect = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.value && e.target.value.length > 0) {
            console.log(`Setting patient: ${e.target.value}`)
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

    const measurementTypes: MeasurementType[] = [
        {
            type:  "heart-rate",
            unit: "beats/minute"
        },
        {
            type:  "skin-temperature",
            unit: "degrees Celcius"
        },
        {
            type:  "respiratory-rate",
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
                            <Select placeholder='Select Patient' onChange={onMeasurementTypeSelect} value={measurementType}>
                                {measurementTypes.map((option, idx) => (
                                    <option key={idx} value={option.type}>{option.type}</option>
                                ))}
                            </Select>
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
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ObservationEditComponent;
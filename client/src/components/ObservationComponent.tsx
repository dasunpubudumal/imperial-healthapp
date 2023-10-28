import React, {useEffect, useState} from 'react';
import {
    Center,
    Flex,
    Heading,
    IconButton, ModalOverlay,
    Skeleton,
    Table,
    TableContainer,
    Tbody,
    Td,
    Th,
    Thead,
    Tr, useDisclosure
} from "@chakra-ui/react";
import {PageWrapper} from "./PageWrapper";
import {ObservationResponse} from "../util/models";
import {HTTP_FORBIDDEN, HTTP_SUCCESS, HTTP_UNAUTHORIZED, SESSION_STORAGE_KEY} from "../util/constants";
import {useNavigate} from "react-router-dom";
import {FaEdit} from "react-icons/fa";
import ObservationEditComponent from "./ObservationEditComponent";
import {DeleteIcon} from "@chakra-ui/icons";
import ObservationDeleteComponent from "./ObservationDeleteComponent";

const ObservationComponent = () => {

    const [observations, setObservations]
        = useState<[ObservationResponse] | null>(null);
    const [isError, setError] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);
    const [selectedObservation, setSelectedObservation]
        = useState<ObservationResponse>({
        id: '',
        date: '',
        patient: 0,
        value: 0.0,
        measurementType: '',
        unit: ''
    });

    const OverlayOne = () => (
        <ModalOverlay
            bg='blackAlpha.300'
            backdropFilter='blur(10px)'
        />
    );

    const {isOpen, onOpen, onClose} = useDisclosure();
    const {
        isOpen: isOpenDeleteModal,
        onOpen: onOpenDeleteModal,
        onClose: onCloseDeletetModal
    } = useDisclosure()
    const [overlay, setOverlay] = React.useState(<OverlayOne/>)

    const navigation = useNavigate();

    const onEditClick = (observation: ObservationResponse) => {
        setSelectedObservation(observation);
        setOverlay(<OverlayOne/>);
        onOpen();
    }

    const onDeleteClick = (observation: ObservationResponse) => {
        setSelectedObservation(observation);
        setOverlay(<OverlayOne/>);
        onOpenDeleteModal();
    }

    const deleteRecord = async (observationId: string): Promise<boolean> => {
        try {
            const deleteReq = await fetch(`/observations/${observationId}`, {
                method: 'DELETE',
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': `Bearer ${sessionStorage.getItem(SESSION_STORAGE_KEY)}`
                }
            });
            if (deleteReq.status === HTTP_FORBIDDEN || deleteReq.status === HTTP_UNAUTHORIZED) {
                sessionStorage.removeItem(SESSION_STORAGE_KEY);
                navigation('/', {
                    state: {
                        redirect: true
                    }
                });
                return false;
            }
            if (deleteReq.status !== HTTP_SUCCESS) {
                setError(true);
            } else {
                setError(false);
                await loadObservations();
                return true;
            }
        } catch (err) {
            setError(true);
            return false;
        }
        return false;
    }

    const loadObservations = async (): Promise<void> => {
        try {
            setLoading(true);
            setError(false);
            const observationsReq = await fetch(`/observations?page=${encodeURIComponent(0)}&size=${20}`,
                {
                    headers: {
                        "Content-Type": "application/json",
                        'Authorization': `Bearer ${sessionStorage.getItem(SESSION_STORAGE_KEY)}`
                    }
                });
            if (observationsReq.status === HTTP_FORBIDDEN || observationsReq.status === HTTP_UNAUTHORIZED) {
                sessionStorage.removeItem(SESSION_STORAGE_KEY);
                navigation('/', {
                    state: {
                        redirect: true
                    }
                });
            }
            if (observationsReq.status !== HTTP_SUCCESS) {
                setError(true);
            }
            const observationsResponse = await observationsReq.json();
            if (observationsResponse.data && observationsResponse.data.content) {
                setObservations(
                    observationsResponse.data.content
                );
                setError(false);
            } else {
                setError(true);
            }
        } catch (err) {
            setError(true);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        (async () => {
            await loadObservations();
        })();
    }, []);

    return (
        <>
            <PageWrapper>
                <ObservationEditComponent isOpen={isOpen}
                                          onOpen={() => onEditClick(selectedObservation)}
                                          onClose={onClose}
                                          overlay={<OverlayOne/>}
                                          observation={selectedObservation}
                                          key={selectedObservation.id}
                />
                <ObservationDeleteComponent isOpen={isOpenDeleteModal}
                                            onOpen={() => onDeleteClick(selectedObservation)}
                                            onClose={onCloseDeletetModal}
                                            overlay={<OverlayOne/>}
                                            observation={selectedObservation}
                                            key={`#${selectedObservation.id}`}
                                            deleteObservation={() => deleteRecord(selectedObservation.id)}
                />
                <Flex alignItems="center" justifyItems="center" alignContent="center" justifyContent="center">
                    <Heading>Observations View</Heading>
                </Flex>
                <Center>
                    <TableContainer p={15} mt={5} w="120vh">
                        <Skeleton isLoaded={!loading}>
                            <Table variant='striped' size="md">
                                <Thead>
                                    <Tr>
                                        <Th>Date</Th>
                                        <Th>Patient</Th>
                                        <Th>Measurement Type</Th>
                                        <Th>Value</Th>
                                        <Th>Unit</Th>
                                        <Th></Th>
                                        <Th></Th>
                                    </Tr>
                                </Thead>
                                <Tbody>
                                    {observations && observations.map((record: ObservationResponse, idx: number) => (
                                        <Tr key={idx}>
                                            <Td>{record.date}</Td>
                                            <Td>{record.patient}</Td>
                                            <Td>{record.measurementType}</Td>
                                            <Td>{record.value}</Td>
                                            <Td>{record.unit}</Td>
                                            <Td><IconButton
                                                isRound={true}
                                                colorScheme='blue'
                                                aria-label='Edit'
                                                icon={<FaEdit/>}
                                                onClick={() => onEditClick(record)}
                                            /></Td>
                                            <Td><IconButton
                                                isRound={true}
                                                colorScheme='red'
                                                aria-label='Delete'
                                                icon={<DeleteIcon/>}
                                                onClick={() => onDeleteClick(record)}
                                            /></Td>
                                        </Tr>
                                    ))}
                                </Tbody>
                            </Table>
                        </Skeleton>
                    </TableContainer>
                </Center>
            </PageWrapper>
        </>
    );
};

export default ObservationComponent;
import React, {useEffect, useState} from 'react';
import {
    Button,
    Center,
    Flex,
    Heading,
    IconButton,
    ModalOverlay,
    Skeleton,
    Table,
    TableContainer,
    Tbody,
    Td,
    Th,
    Thead,
    Tr,
    useDisclosure
} from "@chakra-ui/react";
import {v4 as uuid} from 'uuid';
import {PageWrapper} from "./PageWrapper";
import {ObservationResponse} from "../util/models";
import {HTTP_FORBIDDEN, HTTP_SUCCESS, HTTP_UNAUTHORIZED, SESSION_STORAGE_KEY} from "../util/constants";
import {useNavigate} from "react-router-dom";
import {FaEdit} from "react-icons/fa";
import ObservationEditComponent from "./ObservationEditComponent";
import {AddIcon, DeleteIcon} from "@chakra-ui/icons";
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
    } = useDisclosure();
    const {
        isOpen: isOpenAddModal,
        onOpen: onOpenAddModal,
        onClose: onCloseAddModal
    } = useDisclosure()
    const [overlay, setOverlay] = React.useState(<OverlayOne/>)

    const navigation = useNavigate();

    const onUpdateClose = () => {
        onClose();
        loadObservations().catch(err => setError(true));
    }

    const onAddClose = () => {
        onCloseAddModal();
        loadObservations().catch(err => setError(true));
    }

    const onEditClick = (observation: ObservationResponse) => {
        setSelectedObservation(observation);
        setOverlay(<OverlayOne/>);
        onOpen();
    }

    const onAddClick = () => {
        setOverlay(<OverlayOne/>);
        onOpenAddModal();
    }

    const onDeleteClick = (observation: ObservationResponse) => {
        setSelectedObservation(observation);
        setOverlay(<OverlayOne/>);
        onOpenDeleteModal();
    }

    const deleteRecord = async (observationId: string): Promise<boolean> => {
        try {
            const deleteReq = await fetch(`/api/observations/${observationId}`, {
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
            const observationsReq = await fetch(`/api/observations?page=${encodeURIComponent(0)}&size=${20}`,
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
                                          key={uuid()}
                                          onUpdateClose={onUpdateClose}
                                          isEdit={true}
                />
                <ObservationDeleteComponent isOpen={isOpenDeleteModal}
                                            onOpen={() => onDeleteClick(selectedObservation)}
                                            onClose={onCloseDeletetModal}
                                            overlay={<OverlayOne/>}
                                            observation={selectedObservation}
                                            key={uuid()}
                                            deleteObservation={() => deleteRecord(selectedObservation.id)}
                />
                <ObservationEditComponent
                    isOpen={isOpenAddModal}
                    onClose={onCloseAddModal}
                    onUpdateClose={onAddClose}
                    onOpen={onAddClick}
                    overlay={<OverlayOne/>}
                    observation={
                        {
                            id: '',
                            date: (new Date()).toISOString(),
                            patient: 101,
                            value: 0,
                            measurementType: 'heart-rate',
                            unit: "beats/minute"
                        }
                    }
                    key={uuid()}
                    isEdit={false}
                />
                <Flex alignItems="center" justifyItems="center" alignContent="right" justifyContent="right">
                    <Button
                        px={4}
                        fontSize={'sm'}
                        rounded={'full'}
                        bg={'green.400'}
                        color={'white'}
                        boxShadow={
                            '0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)'
                        }
                        _hover={{
                            bg: 'green.500',
                        }}
                        _focus={{
                            bg: 'green.500',
                        }}
                        rightIcon={<AddIcon />}
                        onClick={() => onAddClick()}
                    >
                        Add Observation
                    </Button>
                </Flex>
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
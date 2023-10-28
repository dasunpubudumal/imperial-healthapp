import React, {useEffect, useState} from 'react';
import {
    Flex,
    Heading,
    Skeleton,
    Table,
    TableCaption,
    TableContainer,
    Tbody,
    Td,
    Th,
    Thead,
    Tr
} from "@chakra-ui/react";
import {PageWrapper} from "./PageWrapper";
import {ObservationResponse} from "../util/models";
import {HTTP_FORBIDDEN, HTTP_SUCCESS, HTTP_UNAUTHORIZED, SESSION_STORAGE_KEY} from "../util/constants";
import {useNavigate} from "react-router-dom";

const ObservationComponent = () => {

    const [observations, setObservations]
        = useState<[ObservationResponse] | null>(null);
    const [isError, setError] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(false);

    const navigation = useNavigate();

    useEffect(() => {
        (async () => {
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
        })();
    }, []);

    return (
        <>
            <PageWrapper>
                <Flex alignItems="center" justifyItems="center" alignContent="center" justifyContent="center">
                    <Heading>Observations View</Heading>
                </Flex>
                <TableContainer p={15} mt={5}>
                    <Skeleton isLoaded={!loading}>
                        <Table variant='striped'>
                            <TableCaption>Observations table</TableCaption>
                            <Thead>
                                <Tr>
                                    <Th>ID</Th>
                                    <Th>Date</Th>
                                    <Th>Patient</Th>
                                    <Th>Measurement Type</Th>
                                    <Th>Value</Th>
                                    <Th>Unit</Th>
                                </Tr>
                            </Thead>
                            <Tbody>
                                {observations && observations.map((record: any, idx: number) => (
                                    <Tr key={idx}>
                                        <Td>{record.id}</Td>
                                        <Td>{record.date}</Td>
                                        <Td>{record.patient}</Td>
                                        <Td>{record.measurementType}</Td>
                                        <Td>{record.value}</Td>
                                        <Td>{record.unit}</Td>
                                    </Tr>
                                ))}
                            </Tbody>
                        </Table>
                    </Skeleton>
                </TableContainer>
            </PageWrapper>
        </>
    );
};

export default ObservationComponent;
import React, {useEffect, useState} from 'react';
import {Flex, Heading, Spacer, Table, TableCaption, TableContainer, Tbody, Td, Th, Thead, Tr} from "@chakra-ui/react";
import {PageWrapper} from "./PageWrapper";

const ObservationComponent = () => {

    const ob = {
        "data": {
        "content": [
            {
                "id": "bb683f36-5924-44e7-8689-b1be60afc80c",
                "date": "2023-09-07T11:23:24Z",
                "patient": 101,
                "value": 37.2,
                "measurementType": "skin-temperature",
                "unit": "degrees Celcius"
            },
            {
                "id": "e348f5bc-38b5-41b3-b892-587412aba2dd",
                "date": "2023-09-06T11:02:44Z",
                "patient": 101,
                "value": 15.0,
                "measurementType": "respiratory-rate",
                "unit": "breaths/minute"
            },
            {
                "id": "8e091fe2-f7df-4dd1-9103-bda525f07768",
                "date": "2023-09-04T08:54:33Z",
                "patient": 102,
                "value": 76.0,
                "measurementType": "heart-rate",
                "unit": "beats/minute"
            },
            {
                "id": "5dee0449-894a-4f4f-879f-025012608bf7",
                "date": "2023-09-04T08:54:33Z",
                "patient": 102,
                "value": 18.0,
                "measurementType": "respiratory-rate",
                "unit": "breaths/minute"
            },
            {
                "id": "9f5a23ed-abca-4f97-a59d-963db33fbed7",
                "date": "2023-09-05T15:12:23Z",
                "patient": 103,
                "value": 37.8,
                "measurementType": "skin-temperature",
                "unit": "degrees Celcius"
            },
            {
                "id": "b3abf2ff-8c04-4c9e-bed1-560b56d7fe25",
                "date": "2023-09-10T15:12:23Z",
                "patient": 110,
                "value": 76.0,
                "measurementType": "heart-rate",
                "unit": "beats/minute"
            },
            {
                "id": "0dd33dac-fad3-4d1b-8dca-120ed440edfb",
                "date": "2023-09-10T15:12:23Z",
                "patient": 120,
                "value": 76.0,
                "measurementType": "heart-rate",
                "unit": "beats/minute"
            },
            {
                "id": "c74e0970-a8d1-4194-bb2f-544ee7773514",
                "date": "2023-09-10T15:12:23Z",
                "patient": 130,
                "value": 76.0,
                "measurementType": "heart-rate",
                "unit": "beats/minute"
            }
        ],
            "pageable": "INSTANCE",
            "first": true,
            "last": true,
            "size": 8,
            "number": 0,
            "sort": {
            "empty": true,
                "sorted": false,
                "unsorted": true
        },
        "numberOfElements": 8,
            "empty": false
    },
        "status": "SUCCESS"
    }

    useEffect(() => {

    }, []);

    return (
        <>
            <PageWrapper>
                <Flex alignItems="center" justifyItems="center" alignContent="center" justifyContent="center">
                    <Heading>Observations View</Heading>
                </Flex>
                <TableContainer pl={5} pr={5} mt={5}>
                    <Table variant='simple'>
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
                            {ob.data.content.map((record: any, idx: number) => (
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
                </TableContainer>
            </PageWrapper>
        </>
    );
};

export default ObservationComponent;
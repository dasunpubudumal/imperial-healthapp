import React from 'react';
import {ObservationResponse} from "../util/models";
import {
    Button,
    Modal,
    Text,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader
} from "@chakra-ui/react";

interface ObservationDeleteProps {
    isOpen: boolean;
    onClose: () => void;
    onOpen: (selectedObservation: any) => void;
    overlay: React.ReactNode;
    observation: ObservationResponse;
    deleteObservation: (observationId: string) => Promise<boolean>
}

const ObservationDeleteComponent: React.FC<ObservationDeleteProps> = ({
                                                                          isOpen,
                                                                          onClose,
                                                                          onOpen,
                                                                          overlay,
                                                                          observation,
                                                                          deleteObservation
                                                                      }) => {
    return (
        <>
            <Modal isCentered isOpen={isOpen} onClose={onClose}>
                {overlay}
                <ModalContent>
                    <ModalHeader>Delete Observation</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <Text>Are you sure?</Text>
                    </ModalBody>
                    <ModalFooter>
                        <Button onClick={onClose}>Close</Button>
                        <Button ml={3} colorScheme="red"
                                onClick={() => {
                                    deleteObservation(observation.id).then(() => onClose());
                                }}>Delete</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default ObservationDeleteComponent;
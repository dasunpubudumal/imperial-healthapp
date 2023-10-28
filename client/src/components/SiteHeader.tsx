'use client';

import {Box, Text, Image, Flex, Heading, HStack, Center, Spacer, Button} from "@chakra-ui/react";
import React from "react";
import {FaHospitalUser} from "react-icons/fa6";

export const SiteHeader: React.FC = () => {
  return (
    <Box borderBottom="1px solid" borderColor="gray.200"
          sx={{'background': 'transparent', 'backdropFilter': 'blur(10px)', 'position': 'sticky', 'top': 0, 'z-index': '100'}}>
        <Flex>
            <Box pl={2}>
                <Image src="/health-icon.png" />
            </Box>
            <Spacer />
            <Flex pr={2} justifyItems="center" alignItems="center">
                <Button rightIcon={<FaHospitalUser />} colorScheme='blue' variant='solid'>
                    Add Observation
                </Button>
            </Flex>
        </Flex>
    </Box>
  );
};

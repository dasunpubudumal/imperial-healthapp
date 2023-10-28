import React, {useState} from 'react';
import {
    Box,
    Flex,
    Heading,
    Text,
    Stack,
    useColorModeValue,
    FormControl,
    FormLabel,
    Input,
    Button,
    FormHelperText,
    FormErrorMessage
} from "@chakra-ui/react";

const LoginComponent = () => {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => setUsername(e.target.value);
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value);
    const isUsernameError = username === ''
    const isPasswordError = password === ''

    return (
        <>
            <Flex
                minH={'100vh'}
                align={'center'}
                justify={'center'}
                bg={useColorModeValue('gray.50', 'gray.800')}>
                <Stack spacing={8} mx={'auto'} maxW={'lg'} py={12} px={6}>
                    <Stack align={'center'}>
                        <Heading fontSize={'4xl'}>Sign in to your account</Heading>
                    </Stack>
                    <Box
                        rounded={'lg'}
                        bg={useColorModeValue('white', 'gray.700')}
                        boxShadow={'lg'}
                        p={8}>
                        <Stack spacing={4}>
                            <FormControl id="username" isInvalid={isUsernameError} isRequired>
                                <FormLabel>Username</FormLabel>
                                <Input value={username} onChange={handleUsernameChange}
                                       placeholder="Enter your username"/>
                                {!isUsernameError ? (
                                    <FormHelperText>
                                        Enter your login username.
                                    </FormHelperText>
                                ) : (
                                    <FormErrorMessage>Username is required.</FormErrorMessage>
                                )}
                            </FormControl>
                            <FormControl id="password" isInvalid={isUsernameError} isRequired>
                                <FormLabel>Password</FormLabel>
                                <Input type="password" value={password} onChange={handlePasswordChange}
                                       placeholder="Enter your password"/>
                                {!isPasswordError ? (
                                    <FormHelperText>
                                        Enter your login password.
                                    </FormHelperText>
                                ) : (
                                    <FormErrorMessage>Password is required.</FormErrorMessage>
                                )}
                            </FormControl>
                            <Stack spacing={10}>
                                <Stack
                                    direction={{base: 'column', sm: 'row'}}
                                    justify={'center'}>
                                    <Text color={'blue.400'}>New User?</Text>
                                </Stack>
                                <Button
                                    bg={'blue.400'}
                                    color={'white'}
                                    _hover={{
                                        bg: 'blue.500',
                                    }}>
                                    Sign in
                                </Button>
                            </Stack>
                        </Stack>
                    </Box>
                </Stack>
            </Flex>
        </>
    );
};

export default LoginComponent;
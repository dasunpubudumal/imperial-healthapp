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
import ErrorComponent from "./ErrorComponent";
import {ErrorResponse} from "react-router-dom";

const LoginComponent = () => {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [validationError, setValidationError] = useState<boolean>(false);
    const [token, setToken] = useState<string | null>(null)
    const [loading, setLoading] = useState<boolean>(false);

    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => setUsername(e.target.value);
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => setPassword(e.target.value);
    const isUsernameError = username === ''
    const isPasswordError = password === ''

    const onSubmit = async (username: string, password: string): Promise<void> => {
        try {
            setLoading(true);
            const fetchReq = await fetch('/auth/login', {
                method: 'POST',
                cache: 'no-cache',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });
            const authResponse = await fetchReq.json();
            if (fetchReq.status === 200) {
                setToken(
                    authResponse.data.token
                );
            } else {
                setValidationError(true);
            }
        } catch (err) {
            setValidationError(true);
        } finally {
            setLoading(false);
        }
    }

    return (
        <>
            <Flex
                minH={'100vh'}
                align={'center'}
                justify={'center'}
                bg={useColorModeValue('gray.50', 'gray.800')}>
                {
                    validationError &&
                    <ErrorComponent message="Error in authenticating the user" title="Invalid credentials"/>
                }
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
                                    }} onClick={() => onSubmit(username, password)}>
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
import React, {useState} from 'react';
import {
    Box,
    Button,
    Flex,
    FormControl,
    FormErrorMessage,
    FormHelperText,
    FormLabel,
    Heading,
    Input,
    Skeleton,
    Stack,
    Text,
    useColorModeValue,
    useToast
} from "@chakra-ui/react";
import ErrorComponent from "./ErrorComponent";
import {useLocation, useNavigate} from "react-router-dom";
import {SESSION_STORAGE_KEY} from "../util/constants";

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

    const toast = useToast();
    const navigate = useNavigate();
    const location = useLocation();

    const onSubmit = async (username: string, password: string): Promise<void> => {
        let error: boolean = false;
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
            let extractedToken = authResponse.data.token;
            if (fetchReq.status === 200 && authResponse.data && extractedToken) {
                setToken(
                    extractedToken
                );
                sessionStorage.setItem(
                    SESSION_STORAGE_KEY,
                    extractedToken
                );
                error = false;
                setValidationError(false);
                navigate('/observations');
            } else {
                error = true;
                setValidationError(true);
            }
        } catch (err) {
            error = true;
            setValidationError(true);
        } finally {
            setLoading(false);
            toast({
                title: error ? 'Login Failed' : 'Successful login.',
                description: "Login",
                status: error ? 'error' : 'success',
                duration: 9000,
                isClosable: true,
            })
        }
    }

    return (
        <>
            {
                location.state && location.state.redirect &&
                <ErrorComponent message="Please login again" title="Token expired."/>
            }
            {
                validationError &&
                <ErrorComponent message="Error in authenticating the user" title="Invalid credentials"/>
            }
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
                                <Skeleton isLoaded={!loading}>
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
                                </Skeleton>
                            </FormControl>
                            <FormControl id="password" isInvalid={isUsernameError} isRequired>
                                <Skeleton isLoaded={!loading}>
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
                                </Skeleton>
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
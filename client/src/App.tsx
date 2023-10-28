import React from "react";
import "./App.css";
import {ChakraBaseProvider, theme} from "@chakra-ui/react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import LoginComponent from "./components/LoginComponent";
import ObservationComponent from "./components/ObservationComponent";

const router = createBrowserRouter([
    {
        path: "/",
        element: <LoginComponent />,
    },
    {
        path: "/observations",
        element: <ObservationComponent />
    }
]);

function App() {
    return (
        <ChakraBaseProvider theme={theme}>
            <RouterProvider router={router}/>
        </ChakraBaseProvider>
    );
}

export default App;

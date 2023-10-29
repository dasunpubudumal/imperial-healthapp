import React from "react";
import "./App.css";
import {ChakraBaseProvider, theme} from "@chakra-ui/react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import LoginComponent from "./components/LoginComponent";
import ObservationComponent from "./components/ObservationComponent";
import ErrorComponent from "./components/ErrorComponent";

const router = createBrowserRouter([
    {
        path: "/",
        element: <LoginComponent/>,
        errorElement: <ErrorComponent message={"Error in loading the page"} title={"Loading Error"} />
    },
    {
        path: "/observations",
        element: <ObservationComponent/>,
        errorElement: <ErrorComponent message={"Error in loading the page"} title={"Loading Error"} />,
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

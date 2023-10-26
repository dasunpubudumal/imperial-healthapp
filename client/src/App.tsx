import React from "react";
import "./App.css";
import { PrimeReactProvider } from "primereact/api";
import { Button } from "primereact/button";
import "primereact/resources/themes/md-light-deeppurple/theme.css";
import { MenuBarComponent } from "./components/MenuBarComponent";

function App() {
  return (
    <PrimeReactProvider>
      <div className="dock-demo">
        <MenuBarComponent />
      </div>
    </PrimeReactProvider>
  );
}

export default App;

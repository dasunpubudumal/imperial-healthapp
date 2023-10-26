import { Menubar } from "primereact/menubar";
import React from "react";
import ClockComponent from "./ClockComponent";
import {items} from "../util/menubarItems";

const start = (
  <img
    alt="logo"
    src="https://primefaces.org/cdn/primereact/images/logo.png"
    height="40"
    className="mr-2"
  ></img>
);
const end = (
  <div style={{ padding: 5 }}>
    <i className="pi pi-video" />
    <span><ClockComponent /></span>
  </div>
);

export const MenuBarComponent = () => {
  return (
    <>
      <Menubar model={items} start={start} end={end} />
    </>
  );
};

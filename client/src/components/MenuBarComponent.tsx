import { Menubar } from "primereact/menubar";
import React from "react";
import ClockComponent from "./ClockComponent";
import {items} from "../util/menubarItems";

const start:  React.JSX.Element = (
  <img
    alt="logo"
    src="/health-icon.png"
    height="40"
    className="mr-2"
  ></img>
);
const end: React.JSX.Element = (
  <div style={{ padding: 5 }}>
    <i className="pi pi-sign-out" />
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

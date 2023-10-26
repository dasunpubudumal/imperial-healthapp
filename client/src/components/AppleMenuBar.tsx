import { Menubar } from "primereact/menubar";
import { Tooltip } from "primereact/tooltip";
import React from "react";
import ClockComponent from "./ClockComponent";

const menubarItems = [
  {
    label: "Finder",
    className: "menubar-root",
  },
  {
    label: "File",
    items: [
      {
        label: "New",
        icon: "pi pi-fw pi-plus",
        items: [
          {
            label: "Bookmark",
            icon: "pi pi-fw pi-bookmark",
          },
          {
            label: "Video",
            icon: "pi pi-fw pi-video",
          },
        ],
      },
      {
        label: "Delete",
        icon: "pi pi-fw pi-trash",
      },
      {
        separator: true,
      },
      {
        label: "Export",
        icon: "pi pi-fw pi-external-link",
      },
    ],
  },
  {
    label: "Edit",
    items: [
      {
        label: "Left",
        icon: "pi pi-fw pi-align-left",
      },
      {
        label: "Right",
        icon: "pi pi-fw pi-align-right",
      },
      {
        label: "Center",
        icon: "pi pi-fw pi-align-center",
      },
      {
        label: "Justify",
        icon: "pi pi-fw pi-align-justify",
      },
    ],
  },
  {
    label: "Users",
    items: [
      {
        label: "New",
        icon: "pi pi-fw pi-user-plus",
      },
      {
        label: "Delete",
        icon: "pi pi-fw pi-user-minus",
      },
      {
        label: "Search",
        icon: "pi pi-fw pi-users",
        items: [
          {
            label: "Filter",
            icon: "pi pi-fw pi-filter",
            items: [
              {
                label: "Print",
                icon: "pi pi-fw pi-print",
              },
            ],
          },
          {
            icon: "pi pi-fw pi-bars",
            label: "List",
          },
        ],
      },
    ],
  },
  {
    label: "Events",
    items: [
      {
        label: "Edit",
        icon: "pi pi-fw pi-pencil",
        items: [
          {
            label: "Save",
            icon: "pi pi-fw pi-calendar-plus",
          },
          {
            label: "Delete",
            icon: "pi pi-fw pi-calendar-minus",
          },
        ],
      },
      {
        label: "Archive",
        icon: "pi pi-fw pi-calendar-times",
        items: [
          {
            label: "Remove",
            icon: "pi pi-fw pi-calendar-minus",
          },
        ],
      },
    ],
  },
  {
    label: "Quit",
  },
];
const start = <i className="pi pi-apple"></i>;
const end = (
  <React.Fragment>
    <i className="pi pi-video" />
    <i className="pi pi-wifi" />
    <i className="pi pi-volume-up" />
    <ClockComponent />
    <i className="pi pi-search" />
    <i className="pi pi-bars" />
  </React.Fragment>
);

export const AppleMenuBar = () => {
  return (
    <>
      <div className="card dock-demo">
        <Tooltip
          className="dark-tooltip"
          target=".dock-advanced .p-dock-action"
          my="center+15 bottom-15"
          at="center top"
          showDelay={150}
        />
        <Menubar model={menubarItems} start={start} end={end} />
      </div>
    </>
  );
};

export const items = [
    {
        label: "File",
        icon: "pi pi-fw pi-file",
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
        label: "Users",
        icon: "pi pi-fw pi-user",
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
    }
];
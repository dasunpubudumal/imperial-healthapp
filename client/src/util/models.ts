export interface ServerResponse<T> {
    data: T;
    status: string;
}

export interface ResponseForObservations {
    content?: [ObservationResponse];
    pageable: Pagable;
    first: boolean;
    last: boolean;
    totalPages: number;
    totalElements: number
    size: number;
    sort?: Sort;
    number: number;
    numberOfElements: number;
    empty: boolean
}

export interface Pagable {
    pageNumber: number;
    pageSize: number;
    sort: Sort;
    offset: number;
    unpaged: boolean;
    paged: boolean
}


export interface Sort {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
}

export interface ObservationResponse {
    id: string;
    date: string;
    patient: number;
    value: number;
    measurementType: string;
    unit: string
}

export interface TokenResponse {
    username?: string;
    token?: string;
}
export interface ServerResponse<T> {
    data: T;
    status: string;
}

export interface ResponseForObservations {
    content?: [ObservationResponse];
    pageable: string;
    first: boolean;
    last: boolean;
    size: number;
    sort?: Sort;
    numberOfElements: number;
    empty: boolean
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
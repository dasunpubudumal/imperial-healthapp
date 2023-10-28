export interface AuthResponse {
    data: TokenResponse,
    status: string
}

export interface TokenResponse {
    username?: string;
    token?: string;
}
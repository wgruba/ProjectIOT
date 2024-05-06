export interface User {
    id: number;
    name: string;
    mail: string;
    permissionLevel: string;
    token: string;
    subscribedEvents: number[]
}
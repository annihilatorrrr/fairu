import { fetchApiEndpoint } from "lib/api";
import { User, File, UserAuthentication,  GetUsers } from "./types";

export async function createUser(body: UserAuthentication): Promise<string | true> {
    const resp = await fetchApiEndpoint("/users/create", {
        body: JSON.stringify(body),
        headers: { "Content-Type": "application/json" },
        method: "POST"
    });

    return resp.success ? true : resp.data.message;
}

export async function fetchUser(id: string): Promise<User | null> {
    const resp = await fetchApiEndpoint<User>(`/users/${id}`);

    return resp.success ? resp.data : null;
}   

export const me: () => Promise<User   | null> = () => fetchUser("@me");

export async function fetchUserFiles(id: string): Promise<File[] | null> {
    const resp = await fetchApiEndpoint<File[]>(`/users/${id}/files`);

    return resp.success ? resp.data : null;
}

export const meFiles: () => Promise<File[] | null> = () => fetchUserFiles("@me");


export async function fetchUserStatistics(): Promise<number> {
    const resp = await fetchApiEndpoint<GetUsers>("/users")
    return resp.success ? resp.data.total_users : -1
}

export async function deleteFile(id: string): Promise<true | string> {
    const resp = await fetchApiEndpoint<File>(`/files/${id}`, {
        method: "DELETE",
    });

    return resp.success ? true : resp.data.message;
}

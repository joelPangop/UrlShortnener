import axios from "axios";
import {UrlShortenerRequest, UrlShortenerResponse} from "../models/models";

const api = axios.create({
    baseURL:  "http://localhost:8080",
    timeout: 10000
});

//Service REST pour raccourcir
export async function shorten(req: UrlShortenerRequest): Promise<UrlShortenerResponse> {
    const { data } = await api.post<UrlShortenerResponse>("/api/shorten", req, {
        headers: { "Content-Type": "application/json" },
    });
    return data;
}

//Service REST pour retrouver l'url d'origine
export async function expand(code: string): Promise<string> {
    const { data } = await api.get(`/api/expand/${encodeURIComponent(code)}`, {
        responseType: "text",
    });
    return data;
}
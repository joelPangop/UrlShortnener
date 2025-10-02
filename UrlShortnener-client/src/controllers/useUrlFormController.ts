import {useEffect, useState} from "react";
import {expand, shorten} from "../services/UrlServices";
import {UrlShortenerRequest, UrlShortenerResponse} from "../models/models";

export const useUrlFormController = () => {
    const [urlShortenerRequest, setUrlShortenerRequest] = useState<UrlShortenerRequest>({url: ""});
    const [urlShortenerResponse, setUrlShortenerResponse] = useState<UrlShortenerResponse | null>(null);
    const [longUrl, setLongUrl] = useState("");
    const [codeInput, setCodeInput] = useState("");
    const [result, setResult] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        setUrlShortenerRequest({ url: longUrl });
    }, [longUrl]);

    const init = () =>{
        setLongUrl("");
        setCodeInput("");
        setResult("");
        setError("");
    }

    const handleChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        if (name === "url") setLongUrl(value);
        if (name === "code") setCodeInput(value);
    }

    async function onShorten(e: React.FormEvent) {
        e.preventDefault();
        try {
            const resp = await shorten(urlShortenerRequest);
            setUrlShortenerResponse(resp);
            init();
        } catch (err: any) {
            setError(err?.response?.data?.error || err.message);
        }
    }

    async function onExpand(e: React.FormEvent) {
        e.preventDefault();
        try {
            const resp = await expand(codeInput);
            setError("");
            setResult(resp);
        } catch (err: any) {
            const resp = JSON.parse(err?.response?.data);
            setError(resp.error || err.message);
        }
    }

    return {
        urlShortenerResponse,
        onShorten,
        onExpand,
        handleChange,
        codeInput,
        longUrl,
        error,
        result
    }

}
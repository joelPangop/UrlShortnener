import React from "react";
import { useUrlFormController } from "../controllers/useUrlFormController";

const UrlShortenerFormView: React.FC = () => {
    const {
        urlShortenerResponse,       // instance ou null
        codeInput,                  // string pour le champ code
        onShorten,                  // (e: FormEvent) => Promise<void>
        onExpand,                   // (e: FormEvent) => Promise<void>
        handleChange,               // (e: ChangeEvent<HTMLInputElement>) => void
        longUrl,
        error,
        result
    } = useUrlFormController();

    return (
        <div className="min-h-screen bg-gray-50 text-gray-900">
            <div className="max-w-2xl mx-auto p-6">
                <h1 className="text-2xl font-bold mb-4">URL Shortener – Client React TS</h1>

                {/* 1) Raccourcir */}
                <section className="mb-8 p-4 bg-white rounded-2xl shadow">
                    <h2 className="font-semibold mb-3">1) Raccourcir une URL</h2>
                    <form onSubmit={onShorten} className="flex gap-2">
                        <input
                            className="flex-1 border rounded-lg px-3 py-2"
                            style={{ width: "300px"}}
                            name="url"
                            placeholder="https://exemple.com/long/path?x=1"
                            value={longUrl}
                            onChange={handleChange}
                        />
                        <br/>
                        <button className="px-4 py-2 rounded-lg bg-blue-600 text-white disabled:opacity-50" type="submit">
                            Raccourcir
                        </button>
                    </form>
                    <br/>
                    {urlShortenerResponse && (
                        <div className="mt-3 text-sm">
                            <div><span className="font-medium">Code:</span> {urlShortenerResponse.code}</div>
                            <div>
                                <span className="font-medium">URL courte:</span>{" "}
                                <a className="text-blue-700 underline"
                                   href={urlShortenerResponse.shortUrl}
                                   target="_blank"
                                   rel="noreferrer">
                                    {urlShortenerResponse.shortUrl}
                                </a>
                            </div>
                        </div>
                    )}
                </section>
                    <br/>
                {/* 2) Retrouver l’URL originale */}
                <section className="mb-8 p-4 bg-white rounded-2xl shadow">
                    <h2 className="font-semibold mb-3">2) Retrouver l’URL originale</h2>
                    <form onSubmit={onExpand} className="flex gap-2 mb-2">
                        <input
                            className="flex-1 border rounded-lg px-3 py-2"
                            placeholder="Code (≤10) – ex: AbCd123"
                            name="code"
                            value={codeInput}
                            onChange={handleChange}
                        />
                        <br/>
                        <button className="px-4 py-2 rounded-lg bg-emerald-600 text-white disabled:opacity-50" type="submit">
                            Retrouver
                        </button>
                    </form>
                    <br/>
                    {!error && result && (
                        <div className="text-sm">
                            <span className="font-medium">URL originale:</span> {result}
                        </div>
                    )}
                </section>
                <br/>
                {error && (
                    <div className="p-3 rounded-lg bg-red-50 text-red-700 border border-red-200">{error}</div>
                )}
            </div>
        </div>
    );
};

export default UrlShortenerFormView;
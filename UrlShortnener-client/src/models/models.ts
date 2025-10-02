export type UrlShortenerRequest = { url: string };

export type UrlShortenerResponse = {
    code: string;
    shortUrl: string;
    originalUrl: string;
};
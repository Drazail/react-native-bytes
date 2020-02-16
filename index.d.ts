
import RNBytes from ".";

export as namespace RNBytes;

export function readFromAndWriteTo(sourcePath : string, targetPath: string, shouldOverWrite: boolean, shoudlAppend: boolean, FirstByteIndex: number, finalByteIndex: number): Promise<string>
export function rm(sourcePath: string):Promise<string>;
export function getFileLength(path: string):Promise<string>;

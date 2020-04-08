import RNBytes from ".";

export as namespace RNBytes;

type Reader = string;
type Buffer = string;
type Writer = string;
type View = string;
type IntView = string;
type StringView = string;

/**
 *
 * @param sourcePath URI of the file to be read
 * @returns {promise} resolves into a string which can be used to refference this reader
 */
export function createReader(sourcePath: string): Promise<Reader>;
/**
 * closes the reader, freeses up its resources and removes the lock on file.
 * reader will not be usable after this.
 * @param reader reference to the reader
 * @returns {promise} resolves into a string which represents the refference to the closed reader
 */
export function closeReader(reader: Reader): Promise<Reader>;
/**
 *
 * @param size buffer size in bytes
 * @returns {promise} resolves into a string which can be used to refference this buffer
 */
export function createBuffer(size: number): Promise<Buffer>;
/**
 *
 * @param size buffer size in bytes
 * @returns {promise} resolves to null
 */
export function removeBuffer(buffer: Buffer): Promise<null>;
/**
 *
 * @param reader reference to a reader
 * @param buffer reference to a buffer
 * @param offset buffer offset
 * <p> The first byte read is stored into element <code>b[0]</code>, the
 * next one into <code>b[1]</code>, and so on. The number of bytes read is,
 * at most, equal to the length of <code>buffer</code>. Let <i>k</i> be the
 * number of bytes actually read; these bytes will be stored in elements
 * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
 * leaving elements <code>b[</code><i>k</i><code>]</code> through
 * <code>b[b.length-1]</code> unaffected.
 * @param length length to be read into the buffer
 * @returns {promise} resolves into a string which can be used to refference this buffer
 */
export function readToBuffer(reader: Reader, buffer: Buffer, offset: number, length: number): Promise<Buffer>;
/**
 * Reads exactly {@code len} bytes from reader source into the buffer,
 * This method reads
 * repeatedly from the file until the requested number of bytes are
 * read. This method blocks until the requested number of bytes are
 * read, the end of the stream is detected, or an exception is thrown.
 * @param reader reference to the reader
 * @param buffer reference to the buffer
 * @param offset buffer offset
 * <p> The first byte read is stored into element <code>b[0]</code>, the
 * next one into <code>b[1]</code>, and so on. The number of bytes read is,
 * at most, equal to the length of <code>buffer</code>. Let <i>k</i> be the
 * number of bytes actually read; these bytes will be stored in elements
 * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
 * leaving elements <code>b[</code><i>k</i><code>]</code> through
 * <code>b[b.length-1]</code> unaffected.
 * @param length length to be read into the buffer
 * @returns {promise} resolves into a string which can be used to refference this buffer
 */
export function readFullyToBuffer(reader: Reader, buffer: Buffer, offset: number, length: number): Promise<Buffer>;

/**
 *
 * @param byteBuffer reference to a buffer
 * @param off buffer offset
 * @param len length to be read
 * @returns {promise} resolves into base64 representation of the buffer
 */
export function getBufferAsBase64(buffer: Buffer, offset: number, length: number): Promise<String>;

/**
 *
 * @param view reference to a view
 * @param buffer reference to the buffer
 * @param srcOffset view offset
 * @param desOffset buffer offset
 * @returns {promise} resolves into a string which can be used to refference this buffer
 */
export function updateBuffer(view: View, buffer: Buffer, srcOffset: number, desOffset: number, length: number): Promise<Buffer>;

/**
 *
 * @param buffer reference to a buffer
 * @param length length of the view
 * @param sourceOffset buffer offset
 * @returns {promise} resolves into a string which can be used to refference this IntView
 */
export function createIntView(buffer: Buffer, length: number, srcOffset: number): Promise<IntView>;

/**
 *
 * @param intView reference to an intView
 * @returns {promise} resolves into an int array representation of this IntView
 */
export function getArrayFromIntView(intView: IntView): Promise<IntView>;


/**
 *
 * @param intView reference to an intView
 * @param readableArray int[]
 * @returns {promise} resolves into a string which can be used to refference this IntView
 */
export function setIntViewAray(intView: IntView, array: number[]): Promise<IntView>;


/**
 *
 * @param buffer reference to a buffer
 * @param length length of the view
 * @param sourceOffset buffer offset
 * @param encoding  one of Standard charsets -
 *                 *Canonical Name for java.io API NOT the java.nio API ie:
 *                 *use "ASCII" not "US-ASCII"
 * @returns {promise} resolves into a string which can be used to refference this StringView
 */

export function createStringView(buffer: Buffer, length: number, srcOffset: number, encoding: String): Promise<StringView>;

/**
 *
 * @param stringView reference to a StringView
 * @returns {promise} resolves into a string representation of this view
 */
export function getStringFromStringView(stringView: StringView): Promise<String>;

/**
 *
 * @param stringView reference to a StringView
 * @param string string to be written into the view
 * @returns {promise} resolves into a string which can be used to refference this StringView
 */
export function setStringViewValue(stringView: StringView, string: String): Promise<StringView>;

/**
 *
 * @param buffer reference to buffer
 * @param targetPath URI of the target file
 * @param shouldOverWrite should overWrite if the file exists
 * @param shouldAppend should append the file if exists
 * @param srcOffset buffer offset
 * @param length number of bytes to be read from the buffer
 * @returns {promise} resolves into target path
 */
export function writeBufferToFile(
    buffer: Buffer, targetPath: string, shouldOverWrite: boolean,
    shouldAppend: boolean, srcOffset: number, length: number): Promise<String>;

/**
 *
 * @param url url
 * @param buffers ReadableArray of Maps {name: string, fileName: string, mediaType:string, buffer:string}
 * @param headers ReadableArray of Maps {key: string, value:string}
 * @param bodies ReadableArray of Maps {key: string, value:string}
 * @returns {promise} resolves to response
 */

export function postBuffers(
    url: string, buffers: { name: string, fileNmae: string, mediaType: string, buffer: Buffer }[],
    headers: { key: string, value: string }[],
    bodies: { key: string, value: string }[],
): Promise<String>;

/**
 *
 * @param buffer reference to buffer
 * @param targetPath URI of the target file
 * @param shouldOverWrite should overWrite if the file exists
 * @param shouldAppend should append the file if exists
 * @param srcOffset buffer offset
 * @param length number of bytes to be read from the buffer
 * @returns {promise} resolves to target file path
 */
export function writeBufferToFile(
    sourcePath: String, targetPath: string, shouldOverWrite: boolean,
    shouldAppend: boolean, FirstByteIndex: number, finalByteIndex: number): Promise<String>;

/**
 * removes the file
 * @param uri file path
 * @returns {promise} resolves to null
 */
export function rm(uri: String): Promise<null>;

/**
 *
 * @param path URI of the file
 * @returns {promise} resolves to file length in bytes
 */
export function getFileLength(uri: String): Promise<number>;


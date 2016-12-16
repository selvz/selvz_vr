/**
 * 
 */
package com.selvz.vr.web.mapper;

/**
 * @author casam
 *
 */
public interface WebMapper<K, T> {
	/**
	 * Converts from Entity to DTO
	 * 
	 * @param source
	 *            Entity
	 * @return the converted External
	 */
	public T convertToExternal(K source);

	/**
	 * Converts from DTO to Entity
	 * 
	 * @param source
	 *            External
	 * @return the converted Entity
	 */
	public K convertToEntity(T source);
}

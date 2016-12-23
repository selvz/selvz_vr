/**
 * 
 */
package com.selvz.vr.web.mapper;

import org.springframework.stereotype.Service;

import com.selvz.vr.repository.db.pojo.Poster;
import com.selvz.vr.web.external.PosterExt;

/**
 * @author casam
 *
 */
@Service
public class PosterWebMapper implements WebMapper<Poster, PosterExt>{

	@Override
	public PosterExt convertToExternal(Poster source) {
		PosterExt posterExt = new PosterExt();
		posterExt.id = source.getId();
		posterExt.format = source.getLabel();
		posterExt.bitmap = source.getBitmap();
		posterExt.file = source.getAddress();
		
		return posterExt;
	}

	@Override
	public Poster convertToEntity(PosterExt source) {
		Poster poster = new Poster();
		poster.setId(source.id);
		poster.setAddress(source.file);
		poster.setLabel(source.format);
		poster.setBitmap(source.bitmap);
		
		return poster;
	}

}

package org.peak15.convertcollection.rules;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.peak15.convertcollection.FatalConversionException;
import org.peak15.convertcollection.workset.ItemFailedException;
import org.peak15.convertcollection.workset.Procedure;

import com.esotericsoftware.minlog.Log;

public final class MP3ify implements Rule {
	
	//TODO: implement common object methods
	
	private static final String LOGNAME = "MP3ify";
	
	private final File src, dest;
	
	private MP3ify(File src, File dest) {
		this.src = src;
		this.dest = dest;
	}
	
	@Override
	public Procedure<File> procedure() {
		return MusicProcedure.INSTANCE;
	}

	@Override
	public TraversalRule traversalRule() {
		return MusicTraversalRule.INSTANCE;
	}

	@Override
	public File directory() {
		return this.src;
	}
	
	public static final class Builder implements Rule.Builder {
		
		public Builder() {}

		@Override
		public Rule build(File directory, List<String> args) throws FatalConversionException {
			// directory is checked upstream
			File src = directory;
			
			if(args.size() < 1) {
				throw new FatalConversionException(new IllegalArgumentException(usage()));
			}
			
			File dest = new File(args.get(0));
			
			if(!(dest.isDirectory() && dest.canRead() && dest.canWrite())) {
				throw new FatalConversionException(
						new IllegalArgumentException("destination-dir must be a directory with read/write access."));
			}
			
			Log.info(LOGNAME, "Ready to convert from " + src + " to " + dest + ".");
			
			return new MP3ify(src, dest);
		}
		
		@Override
		public String usage() {
			return "Usage: java ConvertCollection MP3ify source-dir destination-dir";
		}
	}
	
	private static final class MusicTraversalRule extends TraversalRule {
		
		private static final TraversalRule INSTANCE = new MusicTraversalRule();
		
		private static final IOFileFilter FILTER = FileFilterUtils.or(
				FileFilterUtils.suffixFileFilter(".mp3"),
				FileFilterUtils.suffixFileFilter(".flac"));
		
		private MusicTraversalRule() {
			super(FILTER, null, -1);
		}
		
		@Override
		protected void handleFile(File file, int depth, Collection<File> results) {
			results.add(file);
		}
	}
	
	private static enum MusicProcedure implements Procedure<File> {
		INSTANCE;

		@Override
		public void process(File item) throws ItemFailedException {
			Log.debug(LOGNAME, "PROCESSED FILE " + item);
		}
	}
}

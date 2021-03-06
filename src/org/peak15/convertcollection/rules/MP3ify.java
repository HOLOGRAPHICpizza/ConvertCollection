package org.peak15.convertcollection.rules;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.peak15.convertcollection.ConversionRule;
import org.peak15.convertcollection.FatalConversionException;
import org.peak15.convertcollection.TraversalRule;
import org.peak15.convertcollection.WorkSet.ItemFailedException;
import org.peak15.convertcollection.WorkSet.Procedure;

import com.esotericsoftware.minlog.Log;

/**
 * Instances are immutable value types.
 */
public final class MP3ify implements ConversionRule {
	// tested via RuleTests
	
	private static final String LOGNAME = "MP3ify";
	
	private final File src, dest;
	private final Procedure<File> procedure;
	
	private MP3ify(File src, File dest) {
		this.src = src;
		this.dest = dest;
		
		procedure = new MusicProcedure(this.dest);
	}
	
	@Override
	public Procedure<File> procedure() {
		return procedure;
	}

	@Override
	public TraversalRule traversalRule() {
		return MusicTraversalRule.INSTANCE;
	}

	/**
	 * source directory
	 */
	@Override
	public File sourceDirectory() {
		return this.src;
	}

	/**
	 * @return destination directory
	 */
	public File destinationDirectory() {
		return this.dest;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(!(obj instanceof MP3ify)) {
			return false;
		}
		
		MP3ify mp3 = (MP3ify) obj;
		
		return	mp3.destinationDirectory().equals(this.destinationDirectory())
				&& mp3.sourceDirectory().equals(this.sourceDirectory());
	}
	
	@Override
	public int hashCode() {
		int result = 9001;
		result = 1327 * result + this.destinationDirectory().hashCode();
		result = 1327 * result + this.sourceDirectory().hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("(MP3ify ConversionRule | src: %s, dest: %s)", this.sourceDirectory(), this.destinationDirectory());
	}
	
	public static ConversionRule.Builder builder() {
		return MusicBuilder.INSTANCE;
	}
	
	private static enum MusicBuilder implements ConversionRule.Builder {
		INSTANCE;

		@Override
		public ConversionRule build(File sourceDirectory, List<String> args) throws FatalConversionException {
			if(!(sourceDirectory.isDirectory() && sourceDirectory.canRead() && sourceDirectory.canWrite())) {
				throw new IllegalArgumentException("sourceDirectory must be a directory with read/write permissions.");
			}
			File src = sourceDirectory;
			
			if(args.size() < 1) {
				throw new FatalConversionException(new IllegalArgumentException(usage()));
			}
			
			File dest = new File(args.get(0));
			
			if(!(dest.isDirectory() && dest.canRead() && dest.canWrite())) {
				throw new FatalConversionException(
						new IllegalArgumentException("destinationDirectory must be a directory with read/write access."));
			}
			
			Log.info(LOGNAME, "Ready to convert from " + src + " to " + dest + ".");
			
			return new MP3ify(src, dest);
		}
		
		@Override
		public String usage() {
			return "Usage: java ConvertCollection MP3ify sourceDirectory destinationDirectory";
		}
	}
	
	/**
	 * singleton
	 */
	public static final class MusicTraversalRule extends TraversalRule {
		
		public static final TraversalRule INSTANCE = new MusicTraversalRule();
		
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
	
	/**
	 * Each instance is an immutable value type.
	 * 
	 * Although it is private, instances of it are passed out.
	 */
	private static final class MusicProcedure implements Procedure<File> {
		
		private final File dest;
		
		public MusicProcedure(File destination) {
			if(destination == null) {
				throw new NullPointerException("destination may not be null.");
			}
			
			this.dest = destination;
		}
		
		public File destination() {
			return this.dest;
		}
		
		@Override
		public void process(File item) throws ItemFailedException {
			if(item == null) {
				throw new NullPointerException("item may not be null.");
			}
			
			Log.debug(LOGNAME, "PROCESSED FILE " + item);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == this) {
				return true;
			}
			
			if(!(obj instanceof MusicProcedure)) {
				return false;
			}
			
			MusicProcedure mp = (MusicProcedure) obj;
			
			return	mp.destination().equals(this.destination());
		}
		
		@Override
		public int hashCode() {
			int result = 9001;
			result = 1327 * result + this.destination().hashCode();
			return result;
		}
		
		@Override
		public String toString() {
			return String.format("(MusicProcedure | destination: %s)", this.destination());
		}
	}
}

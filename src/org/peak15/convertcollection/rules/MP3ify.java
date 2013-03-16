package org.peak15.convertcollection.rules;

import java.io.File;
import java.util.List;

import org.peak15.convertcollection.ConversionException;
import org.peak15.convertcollection.FatalConversionException;
import org.peak15.convertcollection.Procedure;

public final class MP3ify implements Rule {
	
	private final File src, dest;
	
	private MP3ify(File src, File dest) {
		this.src = src;
		this.dest = dest;
	}
	
	public static final class Builder implements RuleBuilder {
		
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
			
			return new MP3ify(src, dest);
		}
		
		@Override
		public String usage() {
			return "Usage: java ConvertCollection MP3ify source-dir destination-dir";
		}
	}

	@Override
	public Procedure<File> getProcedure() {
		
		throw new UnsupportedOperationException("Not implemented yet!");
	}
}
package org.peak15.convertcollection.rules;

import java.io.File;

import org.peak15.convertcollection.Procedure;

public interface Rule {
	
	Procedure<File> getProcedure();
}

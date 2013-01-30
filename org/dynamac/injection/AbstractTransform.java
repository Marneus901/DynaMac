/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's abstract injection transform.              *
* For use in client loader scripts for injection.      *
********************************************************/
package org.dynamac.injection;

import org.dynamac.reflection.ClassHook;

public abstract class AbstractTransform {
	public abstract ClassHook injectClass(ClassHook node);
}

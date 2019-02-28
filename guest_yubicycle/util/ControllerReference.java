package jj17.yubicycle.util;

import jj17.yubicycle.view.mainstageController;

public class ControllerReference {

	public static mainstageController controllerRef = null;

	public ControllerReference() {
	}

	public void getRefMainCtrler(mainstageController controller) {
		controllerRef = controller;
		System.out.println(controllerRef);
	}

	public mainstageController setRefMainCtrler() {
		System.out.println(controllerRef);
		return controllerRef;
	}
}

package org.playwrightji.helpers;

import java.lang.ref.WeakReference;

public class ObjectDisposer<Object> {

    public ObjectDisposer(Object obj) throws InterruptedException {
        WeakReference reference = new WeakReference(obj);
        obj = null;
        while(reference.get() != null) {
            System.gc();
            Thread.sleep(50); //wait for gc and check that the weakref is null before proceeding
        }
    }


}

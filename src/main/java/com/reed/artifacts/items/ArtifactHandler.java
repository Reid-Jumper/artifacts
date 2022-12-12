package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactType;

public class ArtifactHandler {
    private AItem a_item;
    private BItem b_item;
    private CItem c_item;
    private DItem d_item;
    private EItem e_item;
    private FItem f_item;
    private GItem g_item;

    public ArtifactHandler() {
        a_item = null;
        b_item = null;
        c_item = null;
        d_item = null;
        e_item = null;
        f_item = null;
        g_item = null;
    }
    public boolean checkOpenArtifact(IArtifactItem item) {
        switch (item.getArtifactType()) {
            case A:
                return a_item == null;
            case B:
                return b_item == null;
            case C:
                return c_item == null;
            case D:
                return d_item == null;
            case E:
                return e_item == null;
            case F:
                return f_item == null;
            case G:
                return g_item == null;
            default:
                return false;
        }
    }public boolean checkOpenArtifact(ArtifactType type) {
        switch (type) {
            case A:
                return a_item == null;
            case B:
                return b_item == null;
            case C:
                return c_item == null;
            case D:
                return d_item == null;
            case E:
                return e_item == null;
            case F:
                return f_item == null;
            case G:
                return g_item == null;
            default:
                return false;
        }
    }
    public void putArtifact(IArtifactItem item, ArtifactType type) {
        switch (type) {
            case A:
                a_item = (AItem)item;
                break;
            case B:
                b_item = (BItem)item;
                break;
            case C:
                c_item = (CItem)item;
                break;
            case D:
                d_item = (DItem)item;
                break;
            case E:
                e_item = (EItem)item;
                break;
            case F:
                f_item = (FItem)item;
                break;
            case G:
                g_item = (GItem)item;
                break;
        }
    }
    public void clearArtifact(ArtifactType type) {
        switch (type) {
            case A:
                a_item = null;
                break;
            case B:
                b_item = null;
                break;
            case C:
                c_item = null;
                break;
            case D:
                d_item = null;
                break;
            case E:
                e_item = null;
                break;
            case F:
                f_item = null;
                break;
            case G:
                g_item = null;
                break;
        }
    }
    public IArtifactItem getArtifact(ArtifactType type) {
        switch (type) {
            case A:
                return a_item;
            case B:
                return b_item;
            case C:
                return c_item;
            case D:
                return d_item;
            case E:
                return e_item;
            case F:
                return f_item;
            default:
                return g_item;
        }
    }
}

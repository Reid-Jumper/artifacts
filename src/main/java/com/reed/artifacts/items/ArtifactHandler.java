package com.reed.artifacts.items;

import com.reed.artifacts.util.ArtifactTrackerTicker;
import com.reed.artifacts.util.ArtifactType;

public class ArtifactHandler {
    private ForgottenHelm a_item;
    private ForgottenChestplate b_item;
    private ForgottenLeggings c_item;
    private ForgottenBoots d_item;
    private ForgottenSword e_item;
    private ForgottenBow f_item;
    private ForgottenShield g_item;
    private ArtifactTrackerTicker a_tracker;
    private ArtifactTrackerTicker b_tracker;
    private ArtifactTrackerTicker c_tracker;
    private ArtifactTrackerTicker d_tracker;
    private ArtifactTrackerTicker e_tracker;
    private ArtifactTrackerTicker f_tracker;
    private ArtifactTrackerTicker g_tracker;

    public ArtifactHandler() {
        a_item = null;
        b_item = null;
        c_item = null;
        d_item = null;
        e_item = null;
        f_item = null;
        g_item = null;
        a_tracker = new ArtifactTrackerTicker();
        b_tracker = new ArtifactTrackerTicker();
        c_tracker = new ArtifactTrackerTicker();
        d_tracker = new ArtifactTrackerTicker();
        e_tracker = new ArtifactTrackerTicker();
        f_tracker = new ArtifactTrackerTicker();
        g_tracker = new ArtifactTrackerTicker();
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
                a_item = (ForgottenHelm)item;
                break;
            case B:
                b_item = (ForgottenChestplate)item;
                break;
            case C:
                c_item = (ForgottenLeggings)item;
                break;
            case D:
                d_item = (ForgottenBoots)item;
                break;
            case E:
                e_item = (ForgottenSword)item;
                break;
            case F:
                f_item = (ForgottenBow)item;
                break;
            case G:
                g_item = (ForgottenShield)item;
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
    public void tick(ArtifactType type) {
        switch (type) {
            case A:
                a_tracker.tickDown();
                break;
            case B:
                b_tracker.tickDown();
                break;
            case C:
                c_tracker.tickDown();
                break;
            case D:
                d_tracker.tickDown();
                break;
            case E:
                e_tracker.tickDown();
                break;
            case F:
                f_tracker.tickDown();
                break;
            case G:
                g_tracker.tickDown();
                break;
        }
    }
    public void tickAll() {
        a_tracker.tickDown();
        b_tracker.tickDown();
        c_tracker.tickDown();
        d_tracker.tickDown();
        e_tracker.tickDown();
        f_tracker.tickDown();
        g_tracker.tickDown();
    }
}

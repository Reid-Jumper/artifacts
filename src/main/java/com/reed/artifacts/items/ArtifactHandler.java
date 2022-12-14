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
            case FORGOTTEN_HELM:
                return a_item == null;
            case FORGOTTEN_CHEST:
                return b_item == null;
            case FORGOTTEN_LEGS:
                return c_item == null;
            case FORGOTTEN_BOOTS:
                return d_item == null;
            case FORGOTTEN_SWORD:
                return e_item == null;
            case FORGOTTEN_BOW:
                return f_item == null;
            case FORGOTTEN_SHIELD:
                return g_item == null;
            default:
                return false;
        }
    }public boolean checkOpenArtifact(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                return a_item == null;
            case FORGOTTEN_CHEST:
                return b_item == null;
            case FORGOTTEN_LEGS:
                return c_item == null;
            case FORGOTTEN_BOOTS:
                return d_item == null;
            case FORGOTTEN_SWORD:
                return e_item == null;
            case FORGOTTEN_BOW:
                return f_item == null;
            case FORGOTTEN_SHIELD:
                return g_item == null;
            default:
                return false;
        }
    }
    public void putArtifact(IArtifactItem item, ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                a_item = (ForgottenHelm)item;
                break;
            case FORGOTTEN_CHEST:
                b_item = (ForgottenChestplate)item;
                break;
            case FORGOTTEN_LEGS:
                c_item = (ForgottenLeggings)item;
                break;
            case FORGOTTEN_BOOTS:
                d_item = (ForgottenBoots)item;
                break;
            case FORGOTTEN_SWORD:
                e_item = (ForgottenSword)item;
                break;
            case FORGOTTEN_BOW:
                f_item = (ForgottenBow)item;
                break;
            case FORGOTTEN_SHIELD:
                g_item = (ForgottenShield)item;
                break;
        }
    }
    public void clearArtifact(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                a_item = null;
                break;
            case FORGOTTEN_CHEST:
                b_item = null;
                break;
            case FORGOTTEN_LEGS:
                c_item = null;
                break;
            case FORGOTTEN_BOOTS:
                d_item = null;
                break;
            case FORGOTTEN_SWORD:
                e_item = null;
                break;
            case FORGOTTEN_BOW:
                f_item = null;
                break;
            case FORGOTTEN_SHIELD:
                g_item = null;
                break;
        }
    }
    public IArtifactItem getArtifact(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                return a_item;
            case FORGOTTEN_CHEST:
                return b_item;
            case FORGOTTEN_LEGS:
                return c_item;
            case FORGOTTEN_BOOTS:
                return d_item;
            case FORGOTTEN_SWORD:
                return e_item;
            case FORGOTTEN_BOW:
                return f_item;
            default:
                return g_item;
        }
    }
    public void tick(ArtifactType type) {
        switch (type) {
            case FORGOTTEN_HELM:
                a_tracker.tickDown();
                break;
            case FORGOTTEN_CHEST:
                b_tracker.tickDown();
                break;
            case FORGOTTEN_LEGS:
                c_tracker.tickDown();
                break;
            case FORGOTTEN_BOOTS:
                d_tracker.tickDown();
                break;
            case FORGOTTEN_SWORD:
                e_tracker.tickDown();
                break;
            case FORGOTTEN_BOW:
                f_tracker.tickDown();
                break;
            case FORGOTTEN_SHIELD:
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

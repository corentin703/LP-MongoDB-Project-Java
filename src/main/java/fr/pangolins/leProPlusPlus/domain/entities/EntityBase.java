package fr.pangolins.leProPlusPlus.domain.entities;

public abstract class EntityBase {
    private int schemaVersion;

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }
}

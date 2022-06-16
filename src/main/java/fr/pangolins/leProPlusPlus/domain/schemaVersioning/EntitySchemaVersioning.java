package fr.pangolins.leProPlusPlus.domain.schemaVersioning;

import fr.pangolins.leProPlusPlus.domain.entities.EntityBase;

public interface EntitySchemaVersioning<TEntity extends EntityBase> {
    void run(TEntity entity);
}

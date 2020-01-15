package rami.project.grey.core.gridsystem;

import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.entity.consumable.attachables.thruster.Thruster;
import rami.project.grey.core.entity.enemy.Muka;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

class SpawningBank {
    private Random random;
    private long xp;
    private LinkedHashMap<Integer, Class<? extends IEntity>> entityWeights;

    SpawningBank(Random rand, long xp) {
        this.random = rand;
        this.xp = xp;
        this.entityWeights = new LinkedHashMap<>();

        // MANUALLY INPUTTING WEIGHTS, WEIGHTS ARE COMPROMISED OUT OF 100%
        this.entityWeights.put(5, Thruster.class);
        this.entityWeights.put(35, Chika.class);
        this.entityWeights.put(40, Muka.class);
        this.entityWeights.put(20, null);
    }
}

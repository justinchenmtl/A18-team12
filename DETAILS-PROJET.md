# Description du projet
## Attributs d'un terrain
* Type:
  1. Agricole (0)
  2. Résidentiel (1)
  3. Commercial (2)
* Prix minimal/maximal en metre carré
* Lots: 
        * Desciption
        * Nombre de droits de passages
        * Nombre de service
        * Superficie
        * Date de mesure

## Attributs d'un lot
* Minimum de 2 services (plus de services peuvent être ajoutés)
* Valeur foncière:
  * Valeur du lots
  * Droit de passage
  * Services
  * 733.77$ (Valeur fixe)
---

  * Valeur foncière totale
  * \+ Taxe scolaire (1.2% de la Valeur foncière totale)
  * \+ Taxe municipale (2,5% de la Valeur foncière totale)

### Valeur du lot
0. Superficie * prix minimum / m<sup>2</sup>
1. Superficie * prix moyen / m<sup>2</sup>
2. Superficie * prix maximal / m<sup>2</sup>

### Valeur des droits de passage
0. 500$ - ( PR ( VL * 5% ) )
1. 500$ - ( PR ( VL * 10% ) )
2. 500$ - ( PR ( VL * 15% ) )
##### * PR = nombre de droits de passage, VL = valeur du lot

### Valeur des services
0. 0.00$
1.  * 0.00$, Superficie &lteq 500 m<sup>2</sup>
    * 500$/service, 500 m<sup>2</sup> < Superfice <= 10 000 m<sup>2</sup>

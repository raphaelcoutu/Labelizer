# ListDto
- Usage : souvent utilisé dans les listages d'entities
- On cache la plupart des relations, sauf si nécessaire
- **On ne met jamais le PARENT jusqu'à preuve du contraire**

# ShowDto
- Pour afficher les Dto (style GET)
- On met toutes les relations

# CreateDto / UpdateDto 
- Utilisé pour créer ou modifier une entité. 
Cela permet de cacher des champs pour ne pas être en mesure de les modifier
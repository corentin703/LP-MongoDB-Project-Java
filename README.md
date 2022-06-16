# LP - Projet Mongo

**Attention à ne pas utiliser le compte Google de Salva !**

[Lien doc ici](https://docs.google.com/document/d/1QGLIGBMOHpZHjhA14QnDS9C6bH6C9EwkgIuBTSKxG8E/edit?usp=sharing)

[Liens / Notation](http://clientserveur.milka.ovh/)

Project Goal : Permettre la mise en relation d'entreprises entre elles en le regroupant sur une plateforme commune où elles 
pourront échanger des avis entre elles
Project Features : 
-   CRUD pour les entreprises (company)
-   CRUD pour les notes (notice)
-   Ajouter des notes aux entreprises

## Services

###  Companies
url : /companies

<table>
    <thead>
        <tr>
            <th width="25%">Url</th>
            <th width="5%">Methode</th>
            <th width="20%">Paramètre</th>
            <th width="50%">Description</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td style="text-align:center;">/</td>
            <td style="text-align:center;">GET</td>
            <td></td>
            <td>Permet de récupérer les informations de toutes les compagnies</td>
        </tr>
        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">GET</td>
            <td></td>
            <td>Permet de récupérer les informations d'une compagnie</td>
        </tr>
        <tr>
            <td style="text-align:center;">/</td>
            <td style="text-align:center;">POST</td>
            <td>String name</td>
            <td>Permet d'ajouter une compagnie en passant un nom en paramètre</td>
        </tr>
        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">PUT</td>
            <td>
                - String name<br>
                - CompanyType companyType</td>
            <td>Permet de modifier une compagnie en indiquant </td>
        </tr>
    </tbody>
</table>

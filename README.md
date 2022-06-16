# LP - Projet Mongo

**Attention à ne pas utiliser le compte Google de Salva !**

[Lien doc ici](https://docs.google.com/document/d/1QGLIGBMOHpZHjhA14QnDS9C6bH6C9EwkgIuBTSKxG8E/edit?usp=sharing)

[Liens / Notation](http://clientserveur.milka.ovh/)

-   Project Goal : Permettre la mise en relation d'entreprises entre elles en le regroupant sur une plateforme commune où elles
    pourront échanger des avis entre elles
-   Project Features :
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
            <td>String id </td>
            <td>Permet de récupérer les informations d'une compagnie à partir de son ID</td>
        </tr>
        <tr>
            <td style="text-align:center;">name/{name}</td>
            <td style="text-align:center;">GET</td>
            <td>String name </td>
            <td>Permet de récupérer les informations d'une compagnie à partir de son name</td>
        </tr>
        <tr>
            <td style="text-align:center;">/search/{name}</td>
            <td style="text-align:center;">GET</td>
            <td>String name </td>
            <td>Permet de lister les compagnies ayant un nom contenant la chaine passée en paramètre</td>
        </tr>
        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">POST</td>
            <td>
                - CreateCompanyRequest request
            <td>Permet de créer une compagnie en indiquant les données via une CreateCompanyRequest</td>
        </tr>
        <tr>
            <td style="text-align:center;">/</td>
            <td style="text-align:center;">PUT</td>
            <td>String id<br>
                - EditCompanyRequest request</td>
            <td>Permet de modifier une compagnie en indiquant son ID et les les données à modifier via une EditCompanyRequest</td>
        </tr>

        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">DELETE</td>
            <td>
                - String id
            <td>Permet de supprimer une compagnie en indiquant son ID</td>
        </tr>
    </tbody>
</table>

###  Notices
url : /notices

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
            <td>Permet de récupérer les informations de toutes les notices</td>
        </tr>
        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">GET</td>
            <td>String id </td>
            <td>Permet de récupérer les informations d'une notice à partir de son ID</td>
        </tr>
        <tr>
            <td style="text-align:center;">titre/{titre}</td>
            <td style="text-align:center;">GET</td>
            <td>String titre </td>
            <td>Permet de récupérer les informations d'une notice à partir de son titre</td>
        </tr>
        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">POST</td>
            <td>
                - CreateNoticeRequest request
            <td>Permet de créer une notice en indiquant les données via une CreateNoticeRequest</td>
        </tr>
        <tr>
            <td style="text-align:center;">/</td>
            <td style="text-align:center;">PUT</td>
            <td>
                - String id<br>
                - EditNoticeRequest request
            </td>
            <td>Permet de modifier une notice en indiquant son ID et les les données à modifier via une EditNoticeRequest</td>
        </tr>
        <tr>
            <td style="text-align:center;">/{id}</td>
            <td style="text-align:center;">DELETE</td>
            <td>
                - String id
            <td>Permet de supprimer une compagnie en indiquant son ID</td>
        </tr>
    </tbody>
</table>

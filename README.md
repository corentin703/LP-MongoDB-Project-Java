# LP - Projet Mongo

### Objectif du projet 

Permettre la mise en relation d'entreprises entre elles en les regroupant 
sur une plateforme commune où elles pourront échanger des avis entre elles.

### Fonctionnalités :
- CRUD pour les entreprises (company)
- CRUD pour les notes (notice)
- Ajouter des notes aux entreprises

## Entités

Les classes correspondantes sont définies dans le package ```fr.pangolins.leProPlusPlus.domain.entities```.

### Entreprise (```Company```)
Il s'agit du document principal. Le *repository* associé correspond à l'interface 
```CompanyRepository```. 

### Type d'entreprise (```CompanyType```)
Second sous-document appartenant à une entreprise, représentant son type.

### Avis (```Notice```)
Cette entité correspond à un sous-document d'une entreprise, regroupant les avis concernant l'entreprise.
Un avis contient une référence vers l'entreprise / client ayant émit l'avis
Cette entité ne possède pas de repository dédié : 
les opérations passent via des pipelines d'agrégation.

## Services / Contrôleurs

Les classes correspondantes sont définies dans le package ```fr.pangolins.leProPlusPlus.domain.entities```.

### Entreprise (*Companies*)
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
            </td>
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
            <td>Permet de supprimer une notice en indiquant son ID</td>
        </tr>
    </tbody>
</table>

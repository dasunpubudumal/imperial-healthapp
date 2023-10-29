# Assessment
![badge](https://github.com/dasunpubudumal/imperial-healthapp/actions/workflows/maven.yml/badge.svg)

The assessment comprises of a task to develop a CRUD application for:

- **C**reating an observation
- **R**eading an observation
- **U**pdating an existing observation
- **R**emoving an existing observation

The data-layer was modeled using [PostgreSQL](https://www.postgresql.org/) and the business-logic was intended to be modeled using [Spring Framework](https://spring.io/projects/spring-framework). [React](https://react.dev/) was used to develop the front-end, because it helps to segregate components and makes data-flow between components easy and concise. In other words, React enforces structure to the UI through components and rules.

# Data Modeling

The initial data model for `Observations` is as follows:

|     **Type**     |       **Date**       | **Patient** | **Value** |    **Unit**     |
| :--------------: | :------------------: | :---------: | :-------: | :-------------: |
|    heart-rate    | 2023-09-06T11:02:44Z |     101     |    65     |  beats/minute   |
| skin-temperature | 2023-09-07T11:23:24Z |     101     |   37.2    | degrees Celsius |
| respiratory-rate | 2023-09-06T11:02:44Z |     101     |    15     | breaths/minute  |
|    heart-rate    | 2023-09-04T08:54:33Z |     102     |    76     |  beats/minute   |
| respiratory-rate | 2023-09-04T08:54:33Z |     102     |    18     | breaths/minute  |
| skin-temperature | 2023-09-05T15:12:23Z |     103     |   37.8    | degrees Celsius |

## Some points on the `Date` values:

- The values are of standard format `ISO 8601`: year, month, day, hour, minutes, seconds, and milliseconds.
- The time part of the values are separated by the `T` character in the date-time string.
- If a time part is included, an offset from UTC can be included using the following characters:
  - `+/-HH:mm`: `HH:mm` offset from UTC
  - `+/-HHmm`: `HHmm` offset from UTC
  - `+/-HH`: `HH` offset from UTC
  - `Z`: No offset from UTC (i.e., `0` offset from UTC)

## 1-NF Check

- All the attributes are atomic (i.e., non-divisible) ✓
  - Is the `Date` field non atomic?
- There are no multi-valued attributes. ✓
- Therefore, the model is already in `1-NF`.' ✓

## 2-NF Check

- The model is already on `1-NF`. ✓
- The relation can be described as observation(<u>`id`</u>, `type`, `date`, `patient`, `value`, `unit`) where `id` is the primary key of the relation `observation`. The ID is a unique attribute assigned upon persistence of a tuple.
  - A composite key `(patient, date, type)` can also be considered as the primary key. I've used a surrogate key `id` which is an `UUID` field, because it might be possible for the user to update the candidate key and arrive at collisions if the composition is used.
- Since the candidate key is singular, the table is in `2-NF`. ✓

## 3-NF Check

- The model is already on `2-NF`. ✓
- `Type` and `Unit` are dependent on each other, **assuming that the units displayed aren't measured by any other unit**.
- Not in `3-NF`. ✗
- Therefore, the relation is segregated into two relations:

### `observations` relation

| ID  |       Type       |         Date         | Patient | Value |
| :-: | :--------------: | :------------------: | :-----: | :---: |
|  1  |    heart-rate    | 2023-09-06T11:02:44Z |   101   |  65   |
|  2  | skin-temperature | 2023-09-07T11:23:24Z |   101   | 37.2  |
|  3  | respirotary-rate | 2023-09-06T11:02:44Z |   101   |  15   |
|  4  |    heart-rate    | 2023-09-04T08:54:33Z |   102   |  76   |
|  5  | respirotary-rate | 2023-09-04T08:54:33Z |   102   |  18   |
|  6  | skin-temperature | 2023-09-05T15:12:23Z |   103   | 37.8  |

**Note:** The `ID` field is NOT a `SERIAL` filed as the table displayed above. It's a `UUID` field that stored UUIDs. I've displayed the IDs as integers in this README.md because it seemed more readable.

### `measurement_types` relation

|     **Type**     |    **Unit**     |
| :--------------: | :-------------: |
|    heart-rate    |  beats/minute   |
| skin-temperature | degrees Celsius |
| respiratory-rate | breaths/minute  |

---
## Notes

- The current application _does not_ perform persistence of patients. Thus, the patients are hard-coded at this point.
  - If patients are to be persisted, a new table `patients` can be added and its primary key will be related to `observations.patient` through a foreign key constraint.
- The application _does_ perform authentication and authorization.
  - Authentication is provided via a `users` table through traditional `(username, password)` combination.
  - Authorization is provided via a `JWT` token validated through a security chain in Spring.
    - Authorization is completely _stateless_. No token storage - either in-memory nor persisted - occur.
    - [Spring OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html) was used to facilitate JWT generation, validation and verification.
    - Tokens are supposed to expire 15 minutes after they were issued. Currently, no refresh-token mechanism exists. In case of an expiry, the UI directs the user for a fresh login.
  - By default, a user with credentials `admin/admin` is populated upon application start. This user has `ADMIN` privileges.
    - API endpoints currently aren't pre-authorized for different roles, although the infrastructure allows it.
- JPA connects the business-logic to the data-layer. Therefore, no JDBC SQL-level logic are present in the codebase.
- The application is dockerized. Thus, it could be run via spinning up a set of containers as specified in the `docker-compose.yml` manifest.
  - The application is seeded with some roles and observations upon startup.
- The `GET /api/observations` API is paginated, and pages can be traversed through the UI.
  - Since the seeding process seed a low count of data, the UI queries from API with the page size as `5` (i.e., `5` records per page).
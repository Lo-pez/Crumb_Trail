# Crumb Trail

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An app for reviewing grocery store products.

### App Evaluation
   - **Category:** Food / Social
   - **Mobile:** Mobile first experience (Reading reviews on the go), Camera used.
   - **Story:** Allows users to read reviews on grocery products to ensure they're buying something that's the right fit for them.
   - **Market:** Anyone that buys groceries and would like to know more about a product before buying it.
   - **Habit:** Users can see new reviews on their favorite grocery store products every day. Users will be notified when a discount is available at stores they select by other users. A potential add on is implementing a points system that gives an arbitrary rank if many users upvote your discount markers or reviews. Users can be given special points/badges for reveiwing previously unreviewed products. 
   - **Scope:** The smaller scope is an app to review products and view products reviews, as well as being able to see discounts and deals marked by other users on stores near you (Or favorite stores). The larger scope includes a social aspect where users can follow each other (Be notified when another user posts a review/discount, view others review/discount score). This larger scope will need to consider users data privacy.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can post a new photo to their feed
* User can create a new account
* User can view their favorite reviews, stores, and products
* User can login
* User can view a stream of products
* User can click on a product and a feed of reviews for it 
* User can scan items
* User can view a feed of reviews
* User can view a detailed review 
* User can view reviews for a branded product
* User can see stores (Using google maps SDK) as well as an average review of their products next to their icon
* User can see latest/nearest deals posted by other users on google map screen
* User can see a store page with all its products and respective reviews
* User can leave comments on reviews 

**Optional Nice-to-have Stories**
* User can search for other users
* User can view profiles
* User can follow/unfollow another user
* User can leave comments on reviews


### 2. Screen Archetypes

**Required Must-have Archetypes**
* Home Screen
   * View highest ratest reviews 
   * View highlighted products
   * View nearby deals 
* Registration screen
   * User can create a new account 
* Login Screen
   * User can login
* Profile Screen
   * User can view their favorite reviews, stores, and products
* Stream Screen
   * User can view a stream of reviews for a certain product
   * Users can see a stream of products that match their search results
   * User can see a stream of stores
* Detailed Review Screen
   * User can view a detailed review 
   * User can leave comments on reviews 
* Detailed Product Screen
   * User can view reviews for a branded product
* Detailed Store Screen
   * User can view reviews for a stores products 
* Creation screen
   * User can create new reviews 
* Search
   * User can search for other products
   * User can search for stores 
* Maps Screen
   * User can see stores (Using google maps SDK) as well as an average review of their products next to their icon/name
* User Review Screen
   * Users can see a stream of users that match their search results (If they search with an @ before the search query)
* Profile Screen (Other users)
   * User can view others favorite reviews, stores, and products, as well as follow/unfollow from this screen


### 3. Navigation

**Tab Navigation** (Tab to Screen)

  * Home Screen
  * Stream Screen
  * Maps Screen
  * View profile

**Flow Navigation** (Screen to Screen)

* Registration Screen
  => Home Screen
* Login Screen
  => Home Screen
* Profile Screen
  => Self reviews
  => View self liked products
  => View self liked reviews 
* Stream Screen
  => Detailed Review Screen
  => Detailed Product Screen
  => Detailed Store Screen
* Product Stream Screen
  => Click on a product to see the Review Stream for it 
* User Stream Screen
  => Click of a profile to see a more detailed version of it
* Creation screen
  => Home Screen(After review is posted)
* Maps
  => Detailed Store Screen
* Home Screen
  => Detailed Review Screen

## Wireframes
<img src="crumb_trail_wireframe.png" width=600>
<img src="crumb_trail_wireframe_2.png" width=600>

## Schema 
### Models
#### Review
   | Property      | Type     | Description | Required |
   | ------------- | -------- | ------------| ---------|
   | objectId      | String   | unique id for the user's review (default field) |[X]|
   | author        | Pointer to User | review author |[X]|
   | image         | File     | image that user posts alongside a review |[]|
   | body          | String   | body for a users review |[]|
   | comments      | Array    | Array of comments on a review |[X]|
   | fdcId         | String   | fdcId for food being reviewed |[X]|
   | reviewLikes   | Array    | Array of users that have liked a review |[X]| 
   | rating        | Number   | Rating a user leaves on their review |[X]|
   | createdAt     | DateTime | date when review is created (default field) |[X]|
   | updatedAt     | DateTime | date when review is last updated (default field) |[X]|
#### Comment
   | Property      | Type     | Description | Required |
   | ------------- | -------- | ------------| ---------|
   | objectId      | String   | unique id for the comment |[X]|
   | parentReview  | Pointer to Review| review being replied to |[X]|
   | commentUser   | Pointer to User| comment author |[X]|
   | commentLikes  | Array    | Array of users that have liked a comment |[X]| 
   | commentBody   | String   | body for a comment on a review |[]|
   | createdAt     | DateTime | date when comment is created (default field) |[X]|
   | updatedAt     | DateTime | date when comment is last updated (default field) |[X]|
#### User
   | Property      | Type     | Description | Required |
   | ------------- | -------- | ------------| ---------|
   | objectId      | String   | unique id for the user's profile |[X]|
   | username      | String   | unique username for a profile |[X]|
   | email         | String   | users verified email |[X]|
   | emailVerified | Boolean  | true if the user has verified email |[X]|
   | password      | String   | users password |[X]|
   | profileImage  | File     | profile image for an account |[]|
   | aboutBody     | String   | body for a users about section |[]|
   | reviewsCount  | Number   | number of reviews a user has posted |[X]|
   | likesCount    | Number   | number of likes a poster has on all of their reviews |[X]|
   | ratingAverage | Number   | Average rating a user leaves on reviews (Private) |[X]|
   | createdAt     | DateTime | date when user created their profile (default field) |[X]|
   | updatedAt     | DateTime | date when user updated their profile (default field) |[X]|
### Networking
#### List of network requests by screen
   - Home Feed Screen
      - (Read/GET) Query all nearby grocery stores
      - (Read/GET) Review of the day
   - Review Stream
      - (Create/POST) Create a new like on a review
      - (Delete) Delete existing like
      - (Create/POST) Create a new comment on a review
      - (Delete) Delete existing comment
   - Create Review Screen
      - (Create/POST) Create a new review object
   - Profile Screen
      - (Read/GET) Query logged in user object
      - (Update/PUT) Update user profile image
      - (Read/GET) All reviews posted by logged in user
      - (Read/GET) Users total likes
      - (Read/GET) Users total reviews

#### [OPTIONAL:] Existing API Endpoints
##### FDA Food API
- Base URL - [https://api.nal.usda.gov/fdc/v1](https://api.nal.usda.gov/fdc/v1)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /food/{fdcId} | Fetches details for one food item by FDC ID |
    `GET`    | /foods | Fetches details for multiple food items using input FDC IDs |
    `GET`    | /foods/list | Returns a paged list of foods, in the 'abridged' format |
    `GET`    | /foods/search | Returns a list of foods that matched search (query) keywords |
    
 ##### Google Maps API
- Base URL - [https://maps.googleapis.com/maps/api/place](https://maps.googleapis.com/maps/api/place/)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /findplacefromtext/output?parameters | Finds a place matching input paramenters |
    `GET`    | /nearbysearch/output?parameters | Returns places within a specified area |
    `GET`    | /foods/list | Returns a paged list of foods, in the 'abridged' format |
    `GET`    | /foods/search | Returns a list of foods that matched search (query) keywords |


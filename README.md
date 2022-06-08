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
* User can login
* User can scan items
* User can view a feed of reviews
* User can see a google maps screen
* User can see latest/nearest deals posted by other users on google map screen
* User can see a store page with all its products and respective reviews

**Optional Nice-to-have Stories**
* User can search for other users
* User can view profiles
* User can follow/unfollow another user
* User can leave comments on reviews


### 2. Screen Archetypes

**Required Must-have Archetypes**
* Registration screen
   * User can register for the app 
* Login Screen
   * User can login
* Self Profile Screen
   * User can view their favorite reviews, stores, and products
* Review Screen
   * User can view reviews for any product they click on
* Review Stream Screen
   * User can view a stream of reviews for a certain product
* Product Stream Screen
   * Users can see a stream of products that match their search results
* Creation screen
   * User can create new reviews 
* Search
   * User can search for other products
   * User can search for stores 
* Maps Screen
   * User can see stores as well as an average review of their products next to their icon
**Optional Nice-to-have Archetypes**
* User Review Screen
   * Users can see a stream of users that match their search results (If they search with an @ before the query)
* Compose Screen
   * User can leave comments on reviews 
* Profile Screen (Other users)
   * User can view others favorite reviews, stores, and products, as well as follow/unfollow from this screen


### 3. Navigation

**Tab Navigation** (Tab to Screen)

  * View product/review stream
  * Search function (For reviews, users, and products)
  * View self profile

**Flow Navigation** (Screen to Screen)

* Registration Screen
  => Home
* Login Screen
  => Home
* Profile Screen
  => Self reviews
  => View self liked products
  => View self liked reviews 
* Review Stream Screen
  => Click on a review to see a more detailed version of it
* Product Stream Screen
  => Click on a product to see the Review Stream for it 
* User Stream Screen
  => Click of a profile to see a more detailed version of it
* Creation screen
  => Home (After review is posted)
* Search
  => Product Stream
  => User Stream
  => Store Stream
* Maps
  =>  Individual stores

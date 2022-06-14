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
  
### 4. Wireframe

<img src="crumb_trail_wireframe.png" width=600>


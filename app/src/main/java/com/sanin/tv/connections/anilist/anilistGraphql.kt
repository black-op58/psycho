package com.sanin.tv.connections.anilist
val standardPageInformation = """  pageInfo {    
        t
fun String.prepare() = this.trimIndent().replace("\n", " ").replace("""  """, "")
fun characterInformation(includeMediaInfo: Boolean) = """    id    name {      
        f
    if (includeMediaInfo) """    media(page: 0,sort:[POPULARITY_DESC,SCORE_DESC]) {      $standardPageInformation      edges {        id        voiceActors {          id,          name {            userPreferred          }          languageV2,          image {            medium,            large          }
        }        characterRole
        node {          id          idMal          isAdult          status          chapters          episodes          nextAiringEpisode { episode }          type
        meanScore          isFavourite          format          bannerImage          countryOfOrigin          coverImage { large }          title {
            english              romaji              userPreferred          }          mediaListEntry {              progress              progressVolumes              
private              score(format: POINT_100)              status
        }
        }
    }
        }""".prepare() else ""}""".prepare()
fun studioInformation(page: Int, perPage: Int) = """    id    name    isFavourite    favourites    media(page: $page, sort:START_DATE_DESC, perPage: $perPage) {      
        $
        meanScore          startDate{ year }          isFavourite
        format          bannerImage          countryOfOrigin          coverImage { large }          title {
            english              romaji              userPreferred          }          mediaListEntry {              progress              progressVolumes              
private              score(format: POINT_100)              status
        }
        }
    }
        }""".prepare()
fun staffInformation(page: Int, perPage: Int) = """    id    name {      
        f
        meanScore          startDate{ year }          isFavourite
        format          bannerImage          countryOfOrigin          coverImage { large }          title {
            english              romaji              userPreferred          }          mediaListEntry {              progress              progressVolumes              
private              score(format: POINT_100)              status
        }
        }
    }
        }""".prepare()
fun userInformation() = """    id    name    about(asHtml: true)    avatar {      
        l
private  score(format: POINT_100)  status}""".prepare()
fun fullMediaInformation(id: Int) = """{  
        M
private      notes      repeat      customLists      updatedAt      startedAt {        
        y
completedAt {        year        month        day}}
reviews(perPage: 3, sort: SCORE_DESC) {      nodes {        id        mediaId        mediaType        summary        body(asHtml: true)        rating
        ratingAmount        userRating        score
private        siteUrl        createdAt        updatedAt        user {          
        i
        }
    }
        }    ${standardMediaInformation()}    source    duration    season    seasonYear    startDate {      year      month      day    }    endDate {      year      month      day    }    studios(isMain: true) {      nodes {        id        name        isAnimationStudio        siteUrl        isFavourite        favourites      }
        }    producers: studios(isMain: false) {      nodes {        id        name        isAnimationStudio        siteUrl        isFavourite        favourites      }
        }    description    trailer {      site      id    }    synonyms    tags {      name      rank      isMediaSpoiler    }    characters(sort: [ROLE, FAVOURITES_DESC], perPage: 25, page: 1) {      edges {        role        voiceActors {          id          name {            first            middle            last            full            native            userPreferred          }          image {            large            medium          }          languageV2        }        node {          id          image {            medium          }          name {            userPreferred          }          isFavourite        }
        }
    }    relations {      edges {        relationType(version: 2)        node {
        ${standardMediaInformation()}
        }
    }
        }    staffPreview: staff(perPage: 8, sort: [RELEVANCE, ID]) {      edges {        role        node {          id          image {            large            medium          }          name {            userPreferred          }
        }
    }
        }    recommendations(sort: RATING_DESC) {      nodes {        mediaRecommendation {          ${standardMediaInformation()}
        }
    }
        }    externalLinks {      id      url      site      type      icon      color      language    }  }  Page(page: 1) {    $standardPageInformation    mediaList(isFollowing: true, sort: [STATUS], mediaId: $id) {      id      status      score(format: POINT_100)      progress      progressVolumes      user {        id        name        avatar {          large          medium        }
        }
    }  }}""".prepare()

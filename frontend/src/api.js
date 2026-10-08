const API_URL = '/api'

async function request(
  endpoint,
  options = {}
) {

  const token =
    localStorage.getItem('token')


  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {})
  }


  if (token) {

    headers.Authorization =
      `Bearer ${token}`

  }


  const response =
    await fetch(
      `${API_URL}${endpoint}`,
      {
        ...options,
        headers
      }
    )


  let data = null

  try {

    data =
      await response.json()

  } catch (error) {

    data = null

  }


  /* ========================= */
  /* XỬ LÝ LỖI */
  /* ========================= */

  if (!response.ok) {

    const error =
      new Error(
        data?.message ||
        'Có lỗi xảy ra'
      )

    error.status =
      response.status

    error.data =
      data

    throw error

  }


  return data

}


/* ========================= */
/* GET */
/* ========================= */

function get(endpoint) {

  return request(
    endpoint,
    {
      method: 'GET'
    }
  )

}


/* ========================= */
/* POST */
/* ========================= */

function post(
  endpoint,
  data
) {

  return request(
    endpoint,
    {
      method: 'POST',

      body:
        JSON.stringify(data)
    }
  )

}


/* ========================= */
/* PUT */
/* ========================= */

function put(
  endpoint,
  data
) {

  return request(
    endpoint,
    {
      method: 'PUT',

      body:
        JSON.stringify(data)
    }
  )

}


/* ========================= */
/* DELETE */
/* ========================= */

function remove(endpoint) {

  return request(
    endpoint,
    {
      method: 'DELETE'
    }
  )

}


/* ========================= */
/* EXPORT */
/* ========================= */

export default {

  get,

  post,

  put,

  delete: remove

}
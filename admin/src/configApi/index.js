import axios from 'axios'

const confApi = axios.create({
  baseURL: 'http://192.168.99.100:8080/GeoQuizz/api/'
})
export default confApi
